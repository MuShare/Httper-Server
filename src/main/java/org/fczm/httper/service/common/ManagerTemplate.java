package org.fczm.httper.service.common;

import org.fczm.httper.dao.DeviceDao;
import org.fczm.httper.dao.ProjectDao;
import org.fczm.httper.dao.RequestDao;
import org.fczm.httper.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class ManagerTemplate {

    @Resource
    protected UserDao userDao;

    @Resource
    protected RequestDao requestDao;

    @Resource
    protected DeviceDao deviceDao;

    @Resource
    protected ProjectDao projectDao;

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
}
