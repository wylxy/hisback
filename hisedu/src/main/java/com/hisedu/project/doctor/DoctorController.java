package com.hisedu.project.doctor;


import com.hisedu.framework.web.controller.BaseController;
import com.hisedu.framework.web.page.TableDataInfo;
import com.hisedu.project.doctor.domain.Doctor;
import com.hisedu.project.doctor.domain.DoctorConfig;
import com.hisedu.project.doctor.service.DoctorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

}
