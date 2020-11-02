package org.mushare.httper.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.mushare.httper.bean.RequestBean;
import org.mushare.httper.domain.Project;
import org.mushare.httper.domain.Request;
import org.mushare.httper.domain.User;
import org.mushare.httper.service.RequestManager;
import org.mushare.httper.service.common.ManagerTemplate;
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
                                     String parameters, String bodyType, String body, String uid, String pid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        Project project = projectDao.get(pid);
        if (project == null) {
            return null;
        }
        if (project.getUser() != user) {
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
        request.setProject(project);
        if (requestDao.save(request) == null) {
            return null;
        }
        return new RequestBean(request);
    }

    public List<RequestBean> receiveClientRequests(String requestsJSONArray, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        List<RequestBean> requests = new ArrayList<RequestBean>();
        JSONArray requestArray = JSONArray.fromObject(requestsJSONArray);
        for (int i = 0; i < requestArray.size(); i++) {
            JSONObject requestObject = requestArray.getJSONObject(i);
            String url = requestObject.getString("url");
            String method = requestObject.getString("method");
            String headers = requestObject.getString("headers");
            String parameters = requestObject.getString("parameters");
            String bodyType = requestObject.getString("bodyType");
            String body = requestObject.getString("body");
            String rid = requestObject.getString("rid");
            String pid = requestObject.getString("pid");

            long updateAt = requestObject.getLong("updateAt");
            Request request = null;
            // If rid is not empty, try to find this request in peristent store.
            if (!rid.equals("")) {
                request = requestDao.get(rid);
                if (request != null) {
                    // If this request is not belong to this user, that means other account has been used in this device.
                    // Set request to null, so that we can create a new request object for this user.
                    if (request.getUser() != user) {
                        request = null;
                    }
                }
            }
            Project project = projectDao.get(pid);
            // If request is not null, we should update this request.
            // Otherwise, we should created a new request entity for it.
            if (request != null) {
                request.setUrl(url);
                request.setMethod(method);
                request.setUpdateAt(updateAt);
                request.setHeaders(headers);
                request.setParameters(parameters);
                request.setBodyType(bodyType);
                request.setBody(body);
                request.setRevision(requestDao.getMaxRevision(user) + 1);
                // If pid is not empty, try to find a project and set this request's project
                if (!pid.equals("")) {

                    // If project is not null and this project belongs to the user, we can set it to request.
                    if (project != null) {
                        if (project.getUser() == user) {

                        }
                    }
                }
                request.setProject(project);
                requestDao.update(request);
                requests.add(new RequestBean(request));
            } else {
                // Add new request entity.
                requests.add(addNewRequest(url, method, updateAt, headers, parameters, bodyType, body, uid, pid));
            }
        }
        return requests;
    }

    public List<RequestBean> getUpdatedRequestsByRevision(int revision, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        List<RequestBean> requests = new ArrayList<RequestBean>();
        for (Request request : requestDao.findUpdatedByRevision(revision, user)) {
            requests.add(new RequestBean(request));
        }
        return requests;
    }

    public int removeRequestByRid(String rid, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return -1;
        }
        Request request = requestDao.get(rid);
        if (request == null) {
            return -1;
        }
        if (request.getUser() != user) {
            return -1;
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
