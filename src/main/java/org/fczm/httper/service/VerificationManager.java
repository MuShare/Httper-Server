package org.fczm.httper.service;

public interface VerificationManager {

    /**
     * Validate this verification code
     * @param vid
     * @return
     */
    boolean validate(String vid);
}
