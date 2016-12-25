package org.fczm.httper.controller;

import org.fczm.httper.controller.util.ErrorCode;
import org.fczm.httper.controller.util.ResponseTool;
import org.fczm.httper.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        if (userManager.getByIdentifierWithType(email, "email") != null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorEmailExsit);
        }
        final String uid = userManager.addUser(name, "email", email, password);
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("uid", uid);
        }});
    }
}
