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
import java.util.List;

@Controller
@RequestMapping("/api/request")
public class RequestController extends ControllerTemplate {

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public ResponseEntity addRequest(@RequestParam String url, @RequestParam String method, @RequestParam long updateAt,
                                     @RequestParam String headers, @RequestParam String parameters, @RequestParam String bodyType,
                                     @RequestParam String body, HttpServletRequest request) {
        UserBean user = auth(request);
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

    @RequestMapping(value = "/pull", method = RequestMethod.GET)
    public ResponseEntity pullUpdatedRequest(@RequestParam int revision, final HttpServletRequest request) {
        UserBean user = auth(request);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final List<RequestBean> requests = requestManager.getUpdatedRequestsByRevision(revision, user.getUid());
        final int globalRevision = requests.size() == 0? revision: requests.get(requests.size() - 1).getRevision();
        return generateOK(new HashMap<String, Object>(){{
            put("requests", requests);
            put("revision", globalRevision);
        }});
    }

}
