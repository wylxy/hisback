package com.hisedu.project.register.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hisedu.framework.web.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class Register extends BaseEntity {
        private static final long serialVersionUID = 1L;

        /**
         * id
         */
        private Integer id;

        /**
         * 病历号
         */
        private String caseNumber;

        /**
         * 姓名
         */
        private String realName;

        /**
         * 性别
         */
        private String gender;

        /**
         * 身份证号
         */
        private String cardNumber;

        /**
         * 出生日期
         */
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date birthday;

        /**
         * 年龄
         */
        private Integer age;

        /**
         * 年龄类型
         */
        private String ageType;

        /**
         * 家庭住址
         */
        private String homeAddress;

        /**
         * 挂号金额
         */
        private Double registMoney;

        /**
         * 挂号日期
         */
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date visitDate;

        /**
         * 午别
         */
        private String noon;

        /**
         * 挂号科室
         */
        private Integer deptmentId;

        /**
         * 挂号医生
         */
        private Integer employeeId;

        /**
         * 挂号级别
         */
        private String registLevel;

        /**
         * 结算类别
         */
        private String settleCategory;

        /**
         * 病历本要否
         */
        private String isBook;

        /**
         * 收费方式
         */
        private String registMethod;

        /**
         * 看诊状态
         */
        private String visitState;

        /**
         * 科室名称
         */
        private String deptmentName;

        /**
         * 医生姓名
         */
        private String employeeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Double getRegistMoney() {
        return registMoney;
    }

    public void setRegistMoney(Double registMoney) {
        this.registMoney = registMoney;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getNoon() {
        return noon;
    }

    public void setNoon(String noon) {
        this.noon = noon;
    }

    public Integer getDeptmentId() {
        return deptmentId;
    }

    public void setDeptmentId(Integer deptmentId) {
        this.deptmentId = deptmentId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getRegistLevel() {
        return registLevel;
    }

    public void setRegistLevel(String registLevel) {
        this.registLevel = registLevel;
    }

    public String getSettleCategory() {
        return settleCategory;
    }

    public void setSettleCategory(String settleCategory) {
        this.settleCategory = settleCategory;
    }

    public String getIsBook() {
        return isBook;
    }

    public void setIsBook(String isBook) {
        this.isBook = isBook;
    }

    public String getRegistMethod() {
        return registMethod;
    }

    public void setRegistMethod(String registMethod) {
        this.registMethod = registMethod;
    }

    public String getVisitState() {
        return visitState;
    }

    public void setVisitState(String visitState) {
        this.visitState = visitState;
    }

    public String getDeptmentName() {
        return deptmentName;
    }

    public void setDeptmentName(String deptmentName) {
        this.deptmentName = deptmentName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "Register{" +
                "id=" + id +
                ", caseNumber='" + caseNumber + '\'' +
                ", realName='" + realName + '\'' +
                ", gender='" + gender + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                ", ageType='" + ageType + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", registMoney=" + registMoney +
                ", visitDate=" + visitDate +
                ", noon='" + noon + '\'' +
                ", deptmentId=" + deptmentId +
                ", employeeId=" + employeeId +
                ", registLevel='" + registLevel + '\'' +
                ", settleCategory='" + settleCategory + '\'' +
                ", isBook='" + isBook + '\'' +
                ", registMethod='" + registMethod + '\'' +
                ", visitState='" + visitState + '\'' +
                ", deptmentName='" + deptmentName + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}
