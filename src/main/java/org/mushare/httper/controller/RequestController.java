package org.mushare.httper.controller;

import org.mushare.httper.bean.RequestBean;
import org.mushare.httper.bean.UserBean;
import org.mushare.httper.controller.common.ControllerTemplate;
import org.mushare.httper.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/request")
public class RequestController extends ControllerTemplate {

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public ResponseEntity addRequests(@RequestParam String requestsJSONArray, final HttpServletRequest request) {
        UserBean user = auth(request);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        final List<RequestBean> requestBeans = requestManager.receiveClientRequests(requestsJSONArray, user.getUid());
        for (final RequestBean requestBean: requestBeans) {
            results.add(new HashMap<String, Object>() {{
                put("revision", requestBean == null? -1: requestBean.getRevision());
                put("rid", requestBean == null? "": requestBean.getRid());
            }});
        }
        return generateOK(new HashMap<String, Object>(){{
            put("results", results);
            put("revision", requestBeans.get(requestBeans.size() - 1).getRevision());
        }});
    }

    @RequestMapping(value = "/push", method = RequestMethod.DELETE)
    public ResponseEntity deleteRequest(@RequestParam String rid, HttpServletRequest request) {
        UserBean user = auth(request);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final int revision = requestManager.removeRequestByRid(rid, user.getUid());
        if (revision < 0) {
            return generateBadRequest(ErrorCode.ErrorDeleteRequest);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("revision", revision);
        }});
    }

    @RequestMapping(value = "/pull", method = RequestMethod.GET)
    public ResponseEntity pullUpdatedRequest(@RequestParam int revision, final HttpServletRequest request) {
        UserBean user = auth(request);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        List<RequestBean> requests = requestManager.getUpdatedRequestsByRevision(revision, user.getUid());
        final List<RequestBean> updated = new ArrayList<RequestBean>();
        final List<String> deleted = new ArrayList<String>();
        for (RequestBean requestBean: requests) {
            if (requestBean.isDeleted()) {
                deleted.add(requestBean.getRid());
            } else {
                updated.add(requestBean);
            }
        }
        final int globalRevision = (requests.size() == 0)? revision: requests.get(requests.size() - 1).getRevision();
        return generateOK(new HashMap<String, Object>(){{
            put("updated", updated);
            put("deleted", deleted);
            put("revision", globalRevision);
        }});
    }

}
