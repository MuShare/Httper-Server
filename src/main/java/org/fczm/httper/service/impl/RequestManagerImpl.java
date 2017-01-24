package org.fczm.httper.service.impl;

import org.fczm.httper.bean.RequestBean;
import org.fczm.httper.domain.Request;
import org.fczm.httper.domain.User;
import org.fczm.httper.service.RequestManager;
import org.fczm.httper.service.util.ManagerTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestManagerImpl extends ManagerTemplate implements RequestManager {

    public RequestBean addNewRequest(String url, String method, long updateAt, String headers,
                                  String parameters, String bodyType, String body, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        int globalRevision = requestDao.getMaxRevision(user);
        Request request = new Request();
        request.setUrl(url);
        request.setMethod(method);
        request.setUpdateAt(updateAt);
        request.setHeaders(headers);
        request.setParameters(parameters);
        request.setBodyType(bodyType);
        request.setBody(body);
        request.setRevision(globalRevision + 1);
        request.setDeleted(false);
        request.setUser(user);
        if (requestDao.save(request) == null) {
            return null;
        }
        return new RequestBean(request);
    }

    public List getUpdatedRequestsByRevision(int revision, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        List<RequestBean> requests = new ArrayList<RequestBean>();
        for (Request request: requestDao.findUpdatedByRevision(revision, user)) {
            requests.add(new RequestBean(request));
        }
        return requests;
    }

}
