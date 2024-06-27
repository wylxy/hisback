package com.hisedu.project.doctor.mapper;

import com.hisedu.project.doctor.domain.Doctor;
import com.hisedu.project.doctor.domain.DoctorConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorMapper {

    public List<Doctor> selectConfigList(DoctorConfig dc);

    public Boolean addDoctor(Doctor dc);

    public Boolean updateDoctor(Doctor dc);

    public Doctor getLastDoctor();

    Doctor getOne(Integer id);
    public Boolean deleteOne(String id);
    public List<Doctor> getDoctorSelect(Integer ksid);
    Boolean updateState(Integer id, String status);
}
