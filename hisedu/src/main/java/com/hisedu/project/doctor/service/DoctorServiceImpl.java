package com.hisedu.project.doctor.service;


import com.hisedu.project.doctor.domain.Doctor;
import com.hisedu.project.doctor.domain.DoctorConfig;
import com.hisedu.project.doctor.mapper.DoctorMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private DoctorMapper doctorMapper;
    @Override
    public List<Doctor> selectConfigList(DoctorConfig dc) {
        return doctorMapper.selectConfigList(dc);
//        return null;
    }

    @Override
    public Boolean addDoctory(Doctor doctor) {
//        Doctor lDoctor = doctorMapper.getLastDoctor();
//        Integer no = Integer.valueOf(lDoctor.getIdcode());
//        doctor.setIdcode(String.valueOf((no+1)));
        return doctorMapper.addDoctor(doctor);
    }

    @Override
    public Boolean updateDoctory(Doctor doctor) {
//        Doctor lDoctor = doctorMapper.getLastDoctor();
//        Integer no = Integer.valueOf(lDoctor.getIdcode());
//        doctor.setIdcode(String.valueOf((no+1)));
        return doctorMapper.updateDoctor(doctor);
    }

    @Override
    public Doctor selectOne(Integer id) {
        return doctorMapper.getOne(id);
    }

    @Override
    public Boolean deleteOne(String id) {
        return doctorMapper.deleteOne(id);
    }

    @Override
    public Boolean updateState(Integer id,String status) {
        return doctorMapper.updateState(id,status);
    }

    @Override
    public List<Doctor> getDoctorSelect(Integer ksid) {
        return doctorMapper.getDoctorSelect(ksid);
    }
}
