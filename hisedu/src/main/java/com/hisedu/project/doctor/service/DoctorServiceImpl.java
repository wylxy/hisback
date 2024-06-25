package com.hisedu.project.doctor.service;


import com.hisedu.project.doctor.domain.Doctor;
import com.hisedu.project.doctor.domain.DoctorConfig;
import com.hisedu.project.doctor.mapper.DoctorMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private DoctorMapper doctorMapper;
    @Override
    public List<Doctor> selectConfigList(DoctorConfig dc) {
        return doctorMapper.selectConfigList();
//        return null;
    }
}
