package org.fczm.httper.controller;

import org.apache.commons.net.whois.WhoisClient;
import org.fczm.httper.controller.common.ControllerTemplate;
import org.fczm.httper.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/api/internet")
public class InternetController extends ControllerTemplate {

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    public ResponseEntity getRemoteDeviceIpAddress(HttpServletRequest request) {
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
        return generateOK(new HashMap<String, Object>(){{
            put("remote", remoteIp);
        }});
    }

    @RequestMapping(value = "/whois", method = RequestMethod.GET)
    public ResponseEntity whois(@RequestParam String domain) {
        WhoisClient whoisClient = new WhoisClient();
        try {
            whoisClient.connect(WhoisClient.DEFAULT_HOST);
            final String result = whoisClient.query(domain);
            System.out.println(result);
            whoisClient.disconnect();
            return generateOK(new HashMap<String, Object>(){{
                put("info", result);
            }});
        } catch(IOException e) {
            System.err.println("Error I/O exception: " + e.getMessage());
            return generateBadRequest(ErrorCode.ErrorToken);
        }
    }

}
