package org.fczm.httper.controller;

import org.fczm.httper.bean.RequestBean;
import org.fczm.httper.bean.UserBean;
import org.fczm.httper.controller.util.ControllerTemplate;
import org.fczm.httper.controller.util.ErrorCode;
import org.fczm.httper.service.RequestManager;
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
@RequestMapping("/api/request")
public class RequestController extends ControllerTemplate {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity pushRequestEntity(@RequestParam String url, @RequestParam String method, @RequestParam long updateAt,
                                            @RequestParam String headers, @RequestParam String parameters, @RequestParam String bodyType,
                                            @RequestParam String body,  final HttpServletRequest request) {
        String token = request.getHeader("token");
        UserBean user = userManager.authByToken(token);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final RequestBean requestBean = requestManager.addNewRequest(url, method, updateAt, headers, parameters, bodyType, body, user.getUid());
        if (requestBean == null) {
            return generateBadRequest(ErrorCode.ErrorAddRequest);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("revision", requestBean.getRevision());
            put("rid", requestBean.getRid());
        }});
    }

}
