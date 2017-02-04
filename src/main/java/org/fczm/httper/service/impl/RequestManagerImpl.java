package org.fczm.httper.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.fczm.httper.bean.RequestBean;
import org.fczm.httper.domain.Request;
import org.fczm.httper.domain.User;
import org.fczm.httper.service.RequestManager;
import org.fczm.httper.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestManagerImpl extends ManagerTemplate implements RequestManager {

    public int getGlobalRequestRevision(String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return 0;
        }
        return requestDao.getMaxRevision(user);
    }

    public RequestBean addNewRequest(String url, String method, long updateAt, String headers,
                                     String parameters, String bodyType, String body, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        Request request = new Request();
        request.setUrl(url);
        request.setMethod(method);
        request.setUpdateAt(updateAt);
        request.setHeaders(headers);
        request.setParameters(parameters);
        request.setBodyType(bodyType);
        request.setBody(body);
        request.setRevision(requestDao.getMaxRevision(user) + 1);
        request.setDeleted(false);
        request.setUser(user);
        if (requestDao.save(request) == null) {
            return null;
        }
        return new RequestBean(request);
    }

    public List<RequestBean> receiveClientRequests(String requestsJSONArray, String uid) {
        List <RequestBean> requests = new ArrayList<RequestBean>();
        JSONArray requestArray = JSONArray.fromObject(requestsJSONArray);
        for (int i = 0; i < requestArray.size(); i++) {
            JSONObject requestObject = requestArray.getJSONObject(i);
            String url = requestObject.getString("url");
            String method = requestObject.getString("method");
            String headers = requestObject.getString("headers");
            String parameters = requestObject.getString("parameters");
            String bodyType = requestObject.getString("bodyType");
            String body = requestObject.getString("body");
            int updateAt = requestObject.getInt("updateAt");
            // We should add a new request and return its revision id.
            requests.add(addNewRequest(url, method, updateAt, headers, parameters, bodyType, body, uid));
        }
        return requests;
    }

    public List<RequestBean> getUpdatedRequestsByRevision(int revision, String uid) {
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

    public int removeRequestByRid(String rid, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return RemoveFailedNotFoundUser;
        }
        Request request = requestDao.get(rid);
        if (request == null) {
            return RemoveFailedNotFoundRequest;
        }
        if (request.getUser() != user) {
            return RemoveFailedNoPrivilege;
        }
        request.setUrl(null);
        request.setMethod(null);
        request.setUpdateAt(null);
        request.setHeaders(null);
        request.setParameters(null);
        request.setBodyType(null);
        request.setBody(null);
        request.setRevision(requestDao.getMaxRevision(user) + 1);
        request.setDeleted(true);
        requestDao.update(request);
        return request.getRevision();
    }

}
