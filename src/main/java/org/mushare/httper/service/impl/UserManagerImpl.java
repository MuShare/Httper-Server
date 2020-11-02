package org.mushare.httper.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.mushare.common.util.Debug;
import org.mushare.common.util.MengularDocument;
import org.mushare.httper.bean.UserBean;
import org.mushare.httper.bean.VerificationBean;
import org.mushare.httper.domain.Device;
import org.mushare.httper.domain.User;
import org.mushare.httper.domain.Verification;
import org.mushare.httper.service.UserManager;
import org.mushare.httper.service.VerificationManager;
import org.mushare.httper.service.common.ManagerTemplate;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
@RemoteProxy(name = "UserManager")
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    public String addUser(String name, String type, String identifier, String credential) {
        User user = new User();
        user.setName(name);
        user.setType(type);
        user.setCredential(credential);
        user.setIdentifier(identifier);
        user.setAvatar(configComponent.DefaultAvatar);
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
        document.setValue("httpProtocol", configComponent.global.httpProtocol);
        document.setValue("domain", configComponent.global.domain);
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
        if (!appAuth(token)) {
            Debug.error("Access token belongs to another app.");
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
            // Download avatar from facebook when user login at first.
            user.setAvatar(downloadAvatarFromFacebook(token));
            userDao.save(user);
        } else {
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
        document.setValue("httpProtocol", configComponent.global.httpProtocol);
        document.setValue("domain", configComponent.global.domain);
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
        if (System.currentTimeMillis() / 1000L - verification.getCreateAt() > configComponent.global.validity) {
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

    private boolean appAuth(String token) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("https://graph.facebook.com/app")
                    .header("accept", "application/json")
                    .queryString("access_token", token)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (response == null) {
            return false;
        }
        JSONObject userInfo = response.getBody().getObject();
        String appId = userInfo.getString("id");
        return configComponent.facebook.appId.equals(appId);
    }

    private String downloadAvatarFromFacebook(String token) {
        HttpResponse<InputStream> response = null;
        String avatar = configComponent.AvatarPath + File.separator + UUID.randomUUID().toString() + ".jpg";
        try {
            response = Unirest.get("https://graph.facebook.com/me/picture")
                    .header("accept", "image/jpeg")
                    .queryString("width", 480)
                    .queryString("access_token", token)
                    .asBinary();
            File file = new File(configComponent.getCachePath() + avatar);
            FileUtils.copyInputStreamToFile(response.getBody(), file);
        } catch (Exception e) {
            e.printStackTrace();
            return configComponent.DefaultAvatar;
        }
        aliyunOSSComponent.upload(avatar, "image/jpeg");
        return avatar;
    }

}
