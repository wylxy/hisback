package com.hisedu.project.doctor;


import com.hisedu.framework.web.controller.BaseController;
import com.hisedu.framework.web.page.TableDataInfo;
import com.hisedu.project.doctor.domain.Doctor;
import com.hisedu.project.doctor.domain.DoctorConfig;
import com.hisedu.project.doctor.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController extends BaseController {
    @Resource
    private DoctorService doctorService;
    @GetMapping("/list")
    public TableDataInfo list( DoctorConfig dc)
    {
        System.out.println("aaaaa");
        startPage();
        List<Doctor> list = doctorService.selectConfigList(dc);
        return getDataTable(list);
    }

    @GetMapping("/doctorSelect")
    public List<Doctor> doctorSelect( Integer ksid)
    {
        System.out.println("aaaaa");
        List<Doctor> list = doctorService.getDoctorSelect(ksid);
        return list;
    }

    @GetMapping("/getOne")
    public Doctor getOne( Integer id)
    {
        System.out.println("aaaaassss");
        Doctor dc = doctorService.selectOne(id);
        return dc;
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody Doctor doctor){
        System.out.println(doctor.getKsname());
        Boolean dc = doctorService.addDoctory(doctor);
        return dc;
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody Doctor doctor){
        System.out.println(doctor.getId());
        Boolean dc = doctorService.updateDoctory(doctor);
        return dc;
    }

    @GetMapping("/delete")
    public Boolean delete(String id)
    {
        Boolean dc = doctorService.deleteOne(id);
        return dc;
    }

    @GetMapping("/updateStatus")
    public Boolean updateStatus(Integer id,String status)
    {
        Boolean done = doctorService.updateState(id, status);
        return done;
    }


}
