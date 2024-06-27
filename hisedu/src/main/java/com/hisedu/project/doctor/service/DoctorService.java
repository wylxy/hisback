package com.hisedu.project.doctor.service;

import com.hisedu.project.doctor.domain.Doctor;
import com.hisedu.project.doctor.domain.DoctorConfig;

import java.util.List;

public interface DoctorService {
    List<Doctor> selectConfigList(DoctorConfig dc);

    Boolean addDoctory(Doctor doctor);

    Boolean updateDoctory(Doctor doctor);

    Doctor selectOne(Integer id);

    Boolean deleteOne(String id);

    Boolean updateState(Integer id, String status);

    List<Doctor> getDoctorSelect(Integer ksid);
}
