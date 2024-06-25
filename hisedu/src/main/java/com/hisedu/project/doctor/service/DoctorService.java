package com.hisedu.project.doctor.service;

import com.hisedu.project.doctor.domain.Doctor;
import com.hisedu.project.doctor.domain.DoctorConfig;

import java.util.List;

public interface DoctorService {
    List<Doctor> selectConfigList(DoctorConfig dc);
}
