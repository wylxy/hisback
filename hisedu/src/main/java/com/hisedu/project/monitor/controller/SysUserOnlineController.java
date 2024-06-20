package com.hisedu.project.monitor.controller;

import com.alibaba.fastjson.JSON;
import com.hisedu.common.constant.Constants;
import com.hisedu.common.utils.StringUtils;
import com.hisedu.framework.aspectj.lang.annotation.Log;
import com.hisedu.framework.aspectj.lang.enums.BusinessType;
import com.hisedu.framework.redis.RedisCache;
import com.hisedu.framework.security.LoginUser;
import com.hisedu.framework.web.controller.BaseController;
import com.hisedu.framework.web.domain.AjaxResult;
import com.hisedu.framework.web.page.TableDataInfo;
import com.hisedu.project.monitor.domain.SysUserOnline;
import com.hisedu.project.red.domain.RedUser;
import com.hisedu.project.red.service.IRedUserService;
import com.hisedu.project.system.domain.SysUser;
import com.hisedu.project.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 在线用户监控
 *
 * @author hisedu
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
    @Autowired
    private ISysUserOnlineService userOnlineService;

    //是否启用了redis
    @Value("${spring.redis.enabled}")
    private boolean enabledRedis;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IRedUserService redUserService;


    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName) {
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        if (enabledRedis) {
            Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
            for (String key : keys) {
                LoginUser user = redisCache.getCacheObject(key);
                if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                    if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                        userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                    }
                } else if (StringUtils.isNotEmpty(ipaddr)) {
                    if (StringUtils.equals(ipaddr, user.getIpaddr())) {
                        userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                    }
                } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                    if (StringUtils.equals(userName, user.getUsername())) {
                        userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                    }
                } else {
                    userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
                }
            }
            Collections.reverse(userOnlineList);
            userOnlineList.removeAll(Collections.singleton(null));
            return getDataTable(userOnlineList);
        } else {
            List<RedUser> userList = redUserService.selectRedUserList(null);
            for (RedUser redUser : userList) {
                LoginUser user = new LoginUser();
                if (null != redUser) {
                    user = JSON.parseObject(redUser.getLogininfo(), LoginUser.class);
                    SysUser sysUser = JSON.parseObject(redUser.getSysuser(), SysUser.class);
                    Set<String> per = JSON.parseObject(redUser.getPermissions(), Set.class);
                    user.setUser(sysUser);
                    user.setPermissions(per);
                }
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
            Collections.reverse(userOnlineList);
            userOnlineList.removeAll(Collections.singleton(null));
            return getDataTable(userOnlineList);
        }
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        String key = Constants.LOGIN_TOKEN_KEY + tokenId;
        if (enabledRedis) {
            redisCache.deleteObject(key);
        } else {
            redUserService.deleteRedUserByUserKey(key);
        }

        return AjaxResult.success();
    }
}
