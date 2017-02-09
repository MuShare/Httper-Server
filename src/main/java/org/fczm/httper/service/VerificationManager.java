package org.fczm.httper.service;

import org.fczm.httper.bean.VerificationBean;

import javax.servlet.http.HttpSession;

public interface VerificationManager {

    public static final String VerificationFlag = "40288bfe5a1da83a015a1da9e4520000";

    /**
     * Validate this verification code
     * @param vid
     * @return
     */
    VerificationBean validate(String vid);

    /**
     * Get verification from session.
     * @param session
     * @return
     */
    VerificationBean getVerificationFromSession(HttpSession session);
}
