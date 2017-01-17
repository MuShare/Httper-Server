package org.fczm.httper.controller;

import org.fczm.httper.bean.UserBean;
import org.fczm.httper.controller.util.ErrorCode;
import org.fczm.httper.controller.util.ResponseTool;
import org.fczm.httper.service.DeviceManager;
import org.fczm.httper.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private DeviceManager deviceManager;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        if (userManager.getByIdentifierWithType(email, "email") != null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorEmailExist);
        }
        final String uid = userManager.addUser(name, "email", email, password);
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("uid", uid);
        }});
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam String email, @RequestParam String password,
                                @RequestParam String deviceIdentifier, String deviceToken, String os, String lan,
                                HttpServletRequest request) {
        final UserBean user = userManager.getByIdentifierWithType(email, "email");
        if (user == null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorEmailNotExist);
        }
        if (!user.getCredential().equals(password)) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorPasswordWrong);
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
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("token", token);
            put("name", user.getName());
        }});
    }
}
