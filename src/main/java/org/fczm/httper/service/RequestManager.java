package org.fczm.httper.service;

import org.fczm.httper.bean.RequestBean;

import java.util.List;
import java.util.Map;

public interface RequestManager {

    /**
     * Get global request revision
     * @param uid
     * @return
     */
    int getGlobalRequestRevision(String uid);

    /**
     * Add a new request.
     * @param url
     * @param method
     * @param updateAt
     * @param headers
     * @param parameters
     * @param bodyType
     * @param body
     * @param uid
     * @return
     */
    RequestBean addNewRequest(String url, String method, long updateAt, String headers, String parameters,
                              String bodyType, String body, String uid);

    /**
     * Receive multiple request entities from client by JSON string of request array
     * @param requestsJSON
     * @param uid
     * @return the list of request's physical id and revision
     */
    List<RequestBean> receiveClientRequests(String requestsJSON, String uid);

    /**
     * Get all updated request for an user
     * @param revision
     * @param uid
     * @return
     */
    List<RequestBean> getUpdatedRequestsByRevision(int revision, String uid);

    public final static int RemoveFailedNotFoundUser = -2021;
    public final static int RemoveFailedNotFoundRequest = -2022;
    public final static int RemoveFailedNoPrivilege = -2023;

    /**
     * Remove a request by physical id
     * @param rid
     * @param uid
     * @return the newest revision id
     */
    int removeRequestByRid(String rid, String uid);

}
