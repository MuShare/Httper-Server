package org.mushare.httper.service;

import org.mushare.httper.bean.UserBean;

import javax.servlet.http.HttpSession;

public interface UserManager {

    public static final String UserTypeEmail = "email";
    public static final String UserTypeFacebook = "facebook";

    /**
     * Add a user
     * @param name
     * @param type
     * @param identifier
     * @param credential
     * @return
     */
    String addUser(String name, String type, String identifier, String credential);

    /**
     * Send a welcome email to user after signing up.
     * @param uid
     * @return
     */
    boolean sendWelcomeMail(String uid);

    /**
     * Get user by identifier
     * @param identifier
     * @param type
     * @return
     */
    UserBean getByIdentifierWithType(String identifier, String type);

    /**
     * User authentication by login token
     * @param token
     * @return
     */
    UserBean authByToken(String token);

    /**
     * Get a user by facebook's access token. If this user is not existed, create a new one in database.
     * @param token
     * @return
     */
    UserBean getByFacebookAccessToken(String token);

    /**
     * Modify user name
     * @param name
     * @param uid
     * @return
     */
    boolean modifyUserName(String name, String uid);

    /**
     * Send a email with an url to user for modifying password.
     * @param uid
     * @return
     */
    boolean sendModifyPasswordMail(String uid);

    /**
     * Reset password, auth by session.
     * @param password
     * @param session
     * @return
     */
    boolean resetPassword(String password, HttpSession session);

}

