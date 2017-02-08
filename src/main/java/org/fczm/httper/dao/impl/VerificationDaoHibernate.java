package org.fczm.httper.dao.impl;

import org.fczm.common.hibernate.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.VerificationDao;
import org.fczm.httper.domain.Verification;
import org.springframework.stereotype.Repository;

@Repository
public class VerificationDaoHibernate extends PageHibernateDaoSupport<Verification> implements VerificationDao {

    public VerificationDaoHibernate() {
        super();
        setClass(Verification.class);
    }

}
