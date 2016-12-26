package org.fczm.httper.service.util;

import org.fczm.httper.dao.DeviceDao;
import org.fczm.httper.dao.RequestDao;
import org.fczm.httper.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerTemplate {

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected RequestDao requestDao;

    @Autowired
    protected DeviceDao deviceDao;

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

}
