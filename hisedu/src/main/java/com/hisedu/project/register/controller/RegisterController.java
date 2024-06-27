package com.hisedu.project.register.controller;

import com.hisedu.common.constant.UserConstants;
import com.hisedu.common.utils.StringUtils;
import com.hisedu.common.utils.poi.ExcelUtil;
import com.hisedu.framework.aspectj.lang.annotation.Log;
import com.hisedu.framework.aspectj.lang.enums.BusinessType;
import com.hisedu.framework.web.controller.BaseController;
import com.hisedu.framework.web.domain.AjaxResult;
import com.hisedu.framework.web.page.TableDataInfo;
import com.hisedu.project.register.domain.Register;
import com.hisedu.project.register.service.IRegisterService;
import com.hisedu.project.system.domain.SysDictData;
import com.hisedu.project.system.domain.SysDictType;
import com.hisedu.project.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author hisedu
 */
@RestController
@RequestMapping("/register/register")
public class RegisterController extends BaseController
{
    @Autowired
    private IRegisterService registerService;

    /**
     * 查询
     * @param register
     * @return
     */
    @PreAuthorize("@ss.hasPermi('register:register:list')")
    @GetMapping("/list")
    public TableDataInfo list(Register register)
    {
        startPage();
        List<Register> list = registerService.selectRegisterList(register);
        return getDataTable(list);
    }
    /**
     * 新增
     * @param register
     * @return
     */
    @PreAuthorize("@ss.hasPermi('register:register:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Register register)
    {
        register.setCreateBy(getUsername());
        return toAjax(registerService.insertRegister(register));
    }
    /**
     * 通过id查询
     */
    @PreAuthorize("@ss.hasPermi('register:register:edit')")
    @GetMapping(value = "/{id}")
    public AjaxResult dictType(@PathVariable String id)
    {
        Register register = registerService.selectRegisterById(id);
        return AjaxResult.success(register);
    }

    /**
     * 更新
     * @param register
     * @return
     */
    @PreAuthorize("@ss.hasPermi('register:register:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Register register)
    {
        register.setUpdateBy(getUsername());
        return toAjax(registerService.updateRegister(register));
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @PreAuthorize("@ss.hasPermi('register:register:remove')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        registerService.deleteRegisterByIds(ids);
        return success();
    }
}
