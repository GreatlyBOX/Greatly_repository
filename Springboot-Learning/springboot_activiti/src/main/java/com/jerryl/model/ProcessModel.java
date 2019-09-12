package com.jerryl.model;

import java.io.Serializable;

public class ProcessModel implements Serializable {
    /**
     *      * @param unitCode
     *      * @param hospitalCode
     *      * @param acceptPhone
     *      * @param templateCode
     *      * @param flag
     *      * @param content
     *      * @param pushType
     *      * @param userId
     *      * @param planid
     *      * @param process
     */
    private String unitCode;       // 上级单位编号
    private String hospitalCode;//   医院编号
    private String acceptPhone;//    接收人
    private String templateCode;//   模板code
    private String flag;//           上层请求系统（1维保，2一站式，3基础信息，4安全隐患）
    private String content;//        推送内容
    private String pushType;//       推送类型（0验证码，1短信消息）
    private String userId;//         启动流程人用户ID
    private String process;//        流程实例ID
    private String staffList;//      微信发送人
    private String planid;//         计划ID

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getAcceptPhone() {
        return acceptPhone;
    }

    public void setAcceptPhone(String acceptPhone) {
        this.acceptPhone = acceptPhone;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getStaffList() {
        return staffList;
    }

    public void setStaffList(String staffList) {
        this.staffList = staffList;
    }
}
