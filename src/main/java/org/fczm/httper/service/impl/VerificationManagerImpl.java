package org.fczm.httper.service.impl;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.httper.bean.VerificationBean;
import org.fczm.httper.domain.Verification;
import org.fczm.httper.service.VerificationManager;
import org.fczm.httper.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@RemoteProxy(name = "VerificationManager")
public class VerificationManagerImpl extends ManagerTemplate implements VerificationManager {

    public VerificationBean validate(String vid) {
        Verification verification = verificationDao.get(vid);
        if (verification == null) {
            return null;
        }
        if (System.currentTimeMillis() / 1000L - verification.getCreateAt() > configComponent.getValidity()) {
            return null;
        }
        return new VerificationBean(verification);
    }

    @RemoteMethod
    public VerificationBean getVerificationFromSession(HttpSession session) {
        return (VerificationBean) session.getAttribute(VerificationFlag);
    }

}
