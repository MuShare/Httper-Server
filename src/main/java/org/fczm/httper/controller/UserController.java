package org.fczm.httper.controller;

import org.fczm.httper.bean.UserBean;
import org.fczm.httper.controller.common.ControllerTemplate;
import org.fczm.httper.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        return generateOK(new HashMap<String, Object>(){{
            put("uid", uid);
        }});
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam String email, @RequestParam String password,
                                @RequestParam String deviceIdentifier, String deviceToken, String os, String lan,
                                HttpServletRequest request) {
        final UserBean user = userManager.getByIdentifierWithType(email, "email");
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorEmailNotExist);
        }
        if (!user.getCredential().equals(password)) {
            return generateBadRequest(ErrorCode.ErrorPasswordWrong);
        }

        //Login success, register device.
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
        final String token = deviceManager.registerDevice(deviceIdentifier, os, lan, deviceToken, ip, user.getUid());
        return generateOK(new HashMap<String, Object>(){{
            put("token", token);
            put("name", user.getName());
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

}
