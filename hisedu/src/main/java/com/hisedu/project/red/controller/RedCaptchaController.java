package com.hisedu.project.red.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hisedu.framework.aspectj.lang.annotation.Log;
import com.hisedu.framework.aspectj.lang.enums.BusinessType;
import com.hisedu.project.red.domain.RedCaptcha;
import com.hisedu.project.red.service.IRedCaptchaService;
import com.hisedu.framework.web.controller.BaseController;
import com.hisedu.framework.web.domain.AjaxResult;
import com.hisedu.common.utils.poi.ExcelUtil;
import com.hisedu.framework.web.page.TableDataInfo;

/**
 * 验证码Controller
 * 
 * @author hisedu
 * @date 2021-12-10
 */
@RestController
@RequestMapping("/red/captcha")
public class RedCaptchaController extends BaseController
{
    @Autowired
    private IRedCaptchaService redCaptchaService;

    /**
     * 查询验证码列表
     */
    @PreAuthorize("@ss.hasPermi('red:captcha:list')")
    @GetMapping("/list")
    public TableDataInfo list(RedCaptcha redCaptcha)
    {
        startPage();
        List<RedCaptcha> list = redCaptchaService.selectRedCaptchaList(redCaptcha);
        return getDataTable(list);
    }

    /**
     * 导出验证码列表
     */
    @PreAuthorize("@ss.hasPermi('red:captcha:export')")
    @Log(title = "验证码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RedCaptcha redCaptcha)
    {
        List<RedCaptcha> list = redCaptchaService.selectRedCaptchaList(redCaptcha);
        ExcelUtil<RedCaptcha> util = new ExcelUtil<RedCaptcha>(RedCaptcha.class);
        util.exportExcel(response, list, "验证码数据");
    }

    /**
     * 获取验证码详细信息
     */
    @PreAuthorize("@ss.hasPermi('red:captcha:query')")
    @GetMapping(value = "/{captchaId}")
    public AjaxResult getInfo(@PathVariable("captchaId") Long captchaId)
    {
        return AjaxResult.success(redCaptchaService.selectRedCaptchaByCaptchaId(captchaId));
    }

    /**
     * 新增验证码
     */
    @PreAuthorize("@ss.hasPermi('red:captcha:add')")
    @Log(title = "验证码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RedCaptcha redCaptcha)
    {
        return toAjax(redCaptchaService.insertRedCaptcha(redCaptcha));
    }

    /**
     * 修改验证码
     */
    @PreAuthorize("@ss.hasPermi('red:captcha:edit')")
    @Log(title = "验证码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RedCaptcha redCaptcha)
    {
        return toAjax(redCaptchaService.updateRedCaptcha(redCaptcha));
    }

    /**
     * 删除验证码
     */
    @PreAuthorize("@ss.hasPermi('red:captcha:remove')")
    @Log(title = "验证码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{captchaIds}")
    public AjaxResult remove(@PathVariable Long[] captchaIds)
    {
        return toAjax(redCaptchaService.deleteRedCaptchaByCaptchaIds(captchaIds));
    }
}
