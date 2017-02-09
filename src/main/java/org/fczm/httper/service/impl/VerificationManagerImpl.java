package org.fczm.httper.service.impl;

import org.fczm.httper.domain.Verification;
import org.fczm.httper.service.VerificationManager;
import org.fczm.httper.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;

@Service
public class VerificationManagerImpl extends ManagerTemplate implements VerificationManager {

    public boolean validate(String vid) {
        Verification verification = verificationDao.get(vid);
        if (verification == null) {
            return false;
        }
        return System.currentTimeMillis() / 1000L - verification.getCreateAt() <= configComponent.getValidity();
    }
}
