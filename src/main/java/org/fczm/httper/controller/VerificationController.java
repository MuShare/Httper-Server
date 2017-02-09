package org.fczm.httper.controller;

import org.fczm.httper.bean.VerificationBean;
import org.fczm.httper.controller.common.ControllerTemplate;
import org.fczm.httper.service.VerificationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/verification")
public class VerificationController extends ControllerTemplate {

    @RequestMapping(value = "/{vid}")
    public void verification(@PathVariable String vid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerificationBean verificationBean = verificationManager.validate(vid);
        if (verificationBean == null || !verificationBean.isActive()) {
            response.sendRedirect("/overtime.html");
            return;
        }
        request.getSession().setAttribute(VerificationManager.VerificationFlag, verificationBean);
        response.sendRedirect("/password/reset.html");

    }
}
