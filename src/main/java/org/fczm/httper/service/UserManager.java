package org.fczm.httper.service;

import org.fczm.httper.bean.UserBean;

public interface UserManager {

    /**
     * Add a user
     * @param name
     * @param type
     * @param identifier
     * @param credential
     * @return
     */
    String addUser(String name, String type, String identifier, String credential);

    UserBean getByIdentifierWithType(String identifier, String type);
}

