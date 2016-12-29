package org.fczm.httper.controller;

import org.fczm.httper.controller.util.ResponseTool;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/api/internet")
public class InternetController {

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    public ResponseEntity getRemoteDeviceIpAddress(HttpServletRequest request) {
        final String localIp = request.getLocalAddr();
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
        final String remoteIp = ip;
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("remote", remoteIp);
            put("local", localIp);
        }});
    }

}
