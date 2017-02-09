package org.fczm.httper.service.common;

import org.fczm.httper.component.ConfigComponent;
import org.fczm.httper.component.MailComponent;
import org.fczm.httper.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerTemplate {

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected RequestDao requestDao;

    @Autowired
    protected DeviceDao deviceDao;

    @Autowired
    protected ProjectDao projectDao;

    @Autowired
    protected VerificationDao verificationDao;

    @Autowired
    protected MailComponent mailComponent;

    @Autowired
    protected ConfigComponent configComponent;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public RequestDao getRequestDao() {
        return requestDao;
    }

    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public DeviceDao getDeviceDao() {
        return deviceDao;
    }

    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public VerificationDao getVerificationDao() {
        return verificationDao;
    }

    public void setVerificationDao(VerificationDao verificationDao) {
        this.verificationDao = verificationDao;
    }

    public MailComponent getMailComponent() {
        return mailComponent;
    }

    public void setMailComponent(MailComponent mailComponent) {
        this.mailComponent = mailComponent;
    }

    public ConfigComponent getConfigComponent() {
        return configComponent;
    }

    public void setConfigComponent(ConfigComponent configComponent) {
        this.configComponent = configComponent;
    }
}
