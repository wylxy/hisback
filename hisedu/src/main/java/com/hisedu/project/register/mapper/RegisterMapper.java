package com.hisedu.project.register.mapper;

import com.hisedu.project.register.domain.Register;
import com.hisedu.project.system.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author hisedu
 */
public interface RegisterMapper
{

    public List<Register> selectRegisterList(Register register);

    public int insertRegister(Register register);

    public int updateRegister(Register register);

    public Register selectRegisterById(String id);

    public int deleteRegisterById(Long id);

    public void deleteRegisterByIds(Long[] ids);

}
