package org.fczm.httper.service.impl;

import org.fczm.httper.bean.UserBean;
import org.fczm.httper.domain.User;
import org.fczm.httper.service.UserManager;
import org.fczm.httper.service.util.ManagerTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    public String addUser(String name, String type, String identifier, String credential) {
        User user = new User();
        user.setName(name);
        user.setType(type);
        user.setCredential(credential);
        user.setIdentifier(identifier);
        return userDao.save(user);
    }

    public UserBean getByIdentifierWithType(String identifier, String type) {
        User user = userDao.getByIdentifierWithType(identifier, type);
        if (user == null) {
            return null;
        }
        return new UserBean(user);
    }

}
