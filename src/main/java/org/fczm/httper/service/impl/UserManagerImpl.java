package org.fczm.httper.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.common.util.Debug;
import org.fczm.common.util.MengularDocument;
import org.fczm.httper.bean.UserBean;
import org.fczm.httper.bean.VerificationBean;
import org.fczm.httper.domain.Device;
import org.fczm.httper.domain.User;
import org.fczm.httper.domain.Verification;
import org.fczm.httper.service.UserManager;
import org.fczm.httper.service.VerificationManager;
import org.fczm.httper.service.common.ManagerTemplate;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
@RemoteProxy(name = "UserManager")
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    public String addUser(String name, String type, String identifier, String credential) {
        User user = new User();
        user.setName(name);
        user.setType(type);
        user.setCredential(credential);
        user.setIdentifier(identifier);
        return userDao.save(user);
    }

    public boolean sendWelcomeMail(String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return false;
        }
        String rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        MengularDocument document = new MengularDocument(rootPath, 0, "mail/welcome.html", null);
        document.setValue("username", user.getName());
        document.setValue("httpProtocol", configComponent.getHttpProtocol());
        document.setValue("domain", configComponent.getDomain());
        document.setValue("email", user.getIdentifier());
        return mailComponent.send(user.getIdentifier(), "Welcome to Httper cloud", document.getDocument());
    }

    public UserBean getByIdentifierWithType(String identifier, String type) {
        User user = userDao.getByIdentifierWithType(identifier, type);
        if (user == null) {
            return null;
        }
        return new UserBean(user, false);
    }

    public UserBean authByToken(String token) {
        Device device = deviceDao.getByToken(token);
        if (device == null) {
            return null;
        }
        return new UserBean(device.getUser(), false);
    }

    public UserBean getByEmail(String email) {
        User user = userDao.getByIdentifierWithType(email, UserTypeEmail);
        if (user == null) {
            return null;
        }
        return new UserBean(user, false);
    }


    public UserBean getByFacebookAccessToken(String token) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("https://graph.facebook.com/me")
                    .header("accept", "application/json")
                    .queryString("access_token", token)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Debug.error("Cannot get user info from facebook, response is null.");
            return null;
        }
        if (response.getStatus() != HttpStatus.OK.value()) {
            Debug.error("Cannot get user info from facebook, bad request.");
            return null;
        }
        JSONObject userInfo = response.getBody().getObject();
        if (userInfo.has("error")) {
            Debug.error("Malformed access token.");
            return null;
        }
        String userId = userInfo.getString("id");
        String name = userInfo.getString("name");
        User user = userDao.getByIdentifierWithType(userId, UserTypeFacebook);
        if (user == null) {
            user = new User();
            user.setName(name);
            user.setType(UserTypeFacebook);
            user.setCredential(token);
            user.setIdentifier(userId);
            userDao.save(user);
        } else {
            user.setName(name);
            user.setCredential(token);
            userDao.update(user);
        }
        return new UserBean(user, false);
    }

    public boolean modifyUserName(String name, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return false;
        }
        user.setName(name);
        userDao.update(user);
        return true;
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
        verification.setActive(true);
        verification.setUser(user);
        String vid = verificationDao.save(verification);
        if (vid == null) {
            return false;
        }
        String rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        MengularDocument document = new MengularDocument(rootPath, 0, "mail/password.html", null);
        document.setValue("username", user.getName());
        document.setValue("httpProtocol", configComponent.getHttpProtocol());
        document.setValue("domain", configComponent.getDomain());
        document.setValue("vid", vid);
        boolean send =  mailComponent.send(user.getIdentifier(), "Reset yout Httper password", document.getDocument());
        // If send mail failed, verficiation should be deleted.
        if (!send) {
            verificationDao.delete(verification);
        }
        return send;
    }

    @RemoteMethod
    public boolean resetPassword(String password, HttpSession session) {
        if (password.equals("")) {
            return false;
        }
        VerificationBean verificationBean = (VerificationBean) session.getAttribute(VerificationManager.VerificationFlag);
        if (verificationBean == null) {
            return false;
        }
        Verification verification = verificationDao.get(verificationBean.getVid());
        if (verification == null) {
            return false;
        }
        if (verification.getType() != Verification.VerificationModifyPassword) {
            return false;
        }
        if (System.currentTimeMillis() / 1000L - verification.getCreateAt() > configComponent.getValidity()) {
            session.removeAttribute(VerificationManager.VerificationFlag);
            return false;
        }
        User user = verification.getUser();
        user.setCredential(password);
        userDao.update(user);
        // Remove verfication from session.
        session.removeAttribute(VerificationManager.VerificationFlag);
        // Set verifivation not active.
        verification.setActive(false);
        verificationDao.update(verification);
        return true;
    }

}
