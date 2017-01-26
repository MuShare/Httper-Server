package org.fczm.httper.controller;

import org.fczm.httper.bean.RequestBean;
import org.fczm.httper.bean.UserBean;
import org.fczm.httper.controller.util.ControllerTemplate;
import org.fczm.httper.controller.util.ErrorCode;
import org.fczm.httper.domain.Request;
import org.fczm.httper.service.RequestManager;
import org.fczm.httper.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    @RequestMapping(value = "/push/list", method = RequestMethod.POST)
    public ResponseEntity addRequests(HttpServletRequest request) {
        System.out.println(getBody(request));
        return generateOK(new HashMap<String, Object>(){{

        }});
    }

    @RequestMapping(value = "/push", method = RequestMethod.DELETE)
    public ResponseEntity deleteRequest(@RequestParam String rid, HttpServletRequest request) {
        UserBean user = auth(request);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final int revision = requestManager.removeRequestByRid(rid, user.getUid());
        if (revision == RequestManager.RemoveFailedNotFoundRequest) {
            return generateBadRequest(ErrorCode.ErrorDeleteRequestNotFound);
        }
        if (revision == RequestManager.RemoveFailedNoPrivilege) {
            return generateBadRequest(ErrorCode.ErrorDeleteRequestNoPrivilege);
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
        final int globalRevision = requests.size() == 0? revision: requests.get(requests.size() - 1).getRevision();
        return generateOK(new HashMap<String, Object>(){{
            put("updated", updated);
            put("deleted", deleted);
            put("revision", globalRevision);
        }});
    }

    public static String getBody(HttpServletRequest request) {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

}
