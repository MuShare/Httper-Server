package org.mushare.httper.controller;

import org.mushare.httper.bean.UserBean;
import org.mushare.httper.controller.common.ControllerTemplate;
import org.mushare.httper.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/api/user")
public class UserController extends ControllerTemplate {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        if (userManager.getByIdentifierWithType(email, "email") != null) {
            return generateBadRequest(ErrorCode.ErrorEmailExist);
        }
        final String uid = userManager.addUser(name, "email", email, password);
        userManager.sendWelcomeMail(uid);
        return generateOK(new HashMap<String, Object>() {{
            put("uid", uid);
        }});
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam String email, @RequestParam String password,
                                @RequestParam String deviceIdentifier, String deviceToken, String os, String lan,
                                HttpServletRequest request) {
        final UserBean userBean = userManager.getByIdentifierWithType(email, "email");
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorEmailNotExist);
        }
        if (!userBean.getCredential().equals(password)) {
            return generateBadRequest(ErrorCode.ErrorPasswordWrong);
        }
        //Login success, register device.
        final String token = deviceManager.registerDevice(deviceIdentifier, os, lan, deviceToken, getRemoteIP(request), userBean.getUid());
        userBean.safe();
        return generateOK(new HashMap<String, Object>() {{
            put("token", token);
            put("user", userBean);
        }});
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.GET)
    public ResponseEntity sendResetPasswordMail(@RequestParam String email) {
        final UserBean user = userManager.getByIdentifierWithType(email, "email");
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorEmailNotExist);
        }
        if (!userManager.sendModifyPasswordMail(user.getUid())) {
            return generateBadRequest(ErrorCode.ErrorSendResetPasswordMail);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
        }});
    }


    @RequestMapping(value = "/fblogin", method = RequestMethod.POST)
    public ResponseEntity fblogin(@RequestParam String accessToken, @RequestParam String deviceIdentifier,
                                  String deviceToken, String os, String lan, HttpServletRequest request) {
        final UserBean userBean = userManager.getByFacebookAccessToken(accessToken);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorFacebookAccessTokenInvalid);
        }
        //Login success, register device.
        final String token = deviceManager.registerDevice(deviceIdentifier, os, lan, deviceToken, getRemoteIP(request), userBean.getUid());
        userBean.safe();
        return generateOK(new HashMap<String, Object>() {{
            put("token", token);
            put("user", userBean);
        }});
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(HttpServletRequest request) {
        final UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        userBean.safe();
        return generateOK(new HashMap<String, Object>() {{
            put("user", userBean);
        }});
    }

    @RequestMapping(value = "/name", method = RequestMethod.POST)
    public ResponseEntity modifyName(@RequestParam String name, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        userManager.modifyUserName(name, userBean.getUid());
        return generateOK(new HashMap<String, Object>(){{
            put("success", true);
        }});
    }

    private static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
