package org.fczm.httper.service.impl;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.common.util.MengularDocument;
import org.fczm.httper.bean.UserBean;
import org.fczm.httper.component.MailComponent;
import org.fczm.httper.domain.Device;
import org.fczm.httper.domain.User;
import org.fczm.httper.domain.Verification;
import org.fczm.httper.service.UserManager;
import org.fczm.httper.service.common.ManagerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RemoteProxy(name = "UserManager")
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    @Autowired
    private MailComponent mailComponent;

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

    public UserBean authByToken(String token) {
        Device device = deviceDao.getByToken(token);
        if (device == null) {
            return null;
        }
        return new UserBean(device.getUser());
    }

    public UserBean getByEmail(String email) {
        User user = userDao.getByIdentifierWithType(email, "email");
        if (user == null) {
            return null;
        }
        return new UserBean(user);
    }

    @RemoteMethod
    public boolean sendModifyPasswordMail(String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return false;
        }
        Verification verification = new Verification();
        verification.setCreateAt(System.currentTimeMillis() / 1000L);
        verification.setType(Verification.VerificationModifyPassword);
        verification.setUser(user);
        String vid = verificationDao.save(verification);
        if (vid == null) {
            return false;
        }
        String rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        MengularDocument document = new MengularDocument(rootPath, 0, "template/modifyPasswordMail.html", null);
        document.setValue("username", user.getName());
        document.setValue("link", "http://httper.mushare.cn/resetPassword.html?vid=" + vid);
        return mailComponent.send(user.getIdentifier(), "Reset yout Httper password", document.getDocument());
    }

    @RemoteMethod
    public boolean modifyPassword(String code, String password) {
        return false;
    }
}
