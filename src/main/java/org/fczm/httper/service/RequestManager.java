package org.fczm.httper.service;

import org.fczm.httper.bean.RequestBean;

import java.util.List;

public interface RequestManager {

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
     * Get all updated request for an user
     * @param revision
     * @param uid
     * @return
     */
    List getUpdatedRequestsByRevision(int revision, String uid);

}
