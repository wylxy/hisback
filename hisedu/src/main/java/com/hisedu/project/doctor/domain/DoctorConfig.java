package com.hisedu.project.doctor.domain;

import com.hisedu.framework.web.domain.BaseEntity;

public class DoctorConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String idcode;
    private String name;
    private String status;
    private String ksname;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKsname() {
        return ksname;
    }

    public void setKsname(String ksname) {
        this.ksname = ksname;
    }
}
