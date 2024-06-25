package com.hisedu.project.doctor.mapper;

import com.hisedu.project.doctor.domain.Doctor;
import com.hisedu.project.doctor.domain.DoctorConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorMapper {

    public List<Doctor> selectConfigList();
}
