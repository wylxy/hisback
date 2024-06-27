package com.hisedu.project.register.service;

import com.hisedu.project.register.domain.Register;
import com.hisedu.project.system.domain.SysDictData;


import java.util.List;

/**
 * 字典 业务层
 *
 * @author hisedu
 */
public interface IRegisterService
{

    public List<Register> selectRegisterList(Register register);

    public int insertRegister(Register register);

    public Register selectRegisterById(String id);

    public int updateRegister(Register register);

    public void deleteRegisterByIds(Long[] ids);
}
