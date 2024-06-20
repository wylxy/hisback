package com.hisedu.framework.security.service;

import com.alibaba.fastjson.JSON;
import com.hisedu.common.constant.Constants;
import com.hisedu.common.utils.DateUtils;
import com.hisedu.common.utils.IdUtils;
import com.hisedu.common.utils.ServletUtils;
import com.hisedu.common.utils.StringUtils;
import com.hisedu.common.utils.ip.AddressUtils;
import com.hisedu.common.utils.ip.IpUtils;
import com.hisedu.framework.redis.RedisCache;
import com.hisedu.framework.security.LoginUser;
import com.hisedu.framework.security.LoginUserSql;
import com.hisedu.project.red.domain.RedCaptcha;
import com.hisedu.project.red.domain.RedUser;
import com.hisedu.project.red.service.IRedCaptchaService;
import com.hisedu.project.red.service.IRedUserService;
import com.hisedu.project.system.domain.SysUser;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author hisedu
 */
@Component
public class TokenService {
    //是否启用了redis
    @Value("${spring.redis.enabled}")
    private boolean enabledRedis;

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IRedCaptchaService redCaptchaService;

    @Autowired
    private IRedUserService redUserService;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                LoginUser user = null;
                if (enabledRedis) {
                    user = redisCache.getCacheObject(userKey);
                } else {
                    LoginUserSql userSql = new LoginUserSql();
                    RedUser redUser = redUserService.selectRedUserByUserKey(userKey);
                    if (null != redUser) {
                        user = JSON.parseObject(redUser.getLogininfo(), LoginUser.class);
                        SysUser sysUser = JSON.parseObject(redUser.getSysuser(), SysUser.class);
                        Set<String> per = JSON.parseObject(redUser.getPermissions(), Set.class);
                        user.setUser(sysUser);
                        user.setPermissions(per);
                    }
                }
                return user;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            if (enabledRedis) {
                redisCache.deleteObject(userKey);
            } else {
                redUserService.deleteRedUserByUserKey(userKey);
            }
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser 登录人
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());

        if (!enabledRedis) {
            redUserService.deleteRedUserByUserId(loginUser.getUserId());

            SysUser sysUser = loginUser.getUser();
            Set<String> pre = loginUser.getPermissions();
            LoginUser user = loginUser;
            user.setUser(new SysUser());
            user.setPermissions(null);

            RedUser redUser = new RedUser();
            redUser.setUserId(user.getUserId());
            redUser.setUserKey(userKey);
            redUser.setSysuser(JSON.toJSONString(sysUser));
            redUser.setLogininfo(JSON.toJSONString(user));
            redUser.setPermissions(JSON.toJSONString(pre));
            redUser.setCreateTime(DateUtils.getNowDate());
            redUser.setCreateBy("sys");
            Date expreationTime = new Date(loginUser.getExpireTime());
            redUser.setExpreationTime(expreationTime);
            redUser.setCaptchaExpiration(0);
            redUserService.insertRedUser(redUser);
        } else {
            redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
        }

    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
