package com.hisedu.project.register.service.impl;


import com.hisedu.project.register.domain.Register;
import com.hisedu.project.register.mapper.RegisterMapper;
import com.hisedu.project.register.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements IRegisterService
{
    @Autowired
    private RegisterMapper registerMapper;

    @Override
    public List<Register> selectRegisterList(Register register)
    {
        return registerMapper.selectRegisterList(register);
    }
    @Override
    public int insertRegister(Register register)
    {
        int row = registerMapper.insertRegister(register);
        return row;
    }
    @Override
    public Register selectRegisterById(String id)
    {
        Register register = registerMapper.selectRegisterById(id);
        return register;
    }
    @Override
    public int updateRegister(Register register)
    {
        int row = registerMapper.updateRegister(register);
        return row;
    }
    @Override
    public void deleteRegisterByIds(Long[] ids)
    {
        registerMapper.deleteRegisterByIds(ids);
    }
}
