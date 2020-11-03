package org.mushare.httper.dao.impl;

import org.mushare.common.hibernate.support.PageHibernateDaoSupport;
import org.mushare.httper.dao.VerificationDao;
import org.mushare.httper.domain.Verification;
import org.springframework.stereotype.Repository;

@Repository
public class VerificationDaoHibernate extends PageHibernateDaoSupport<Verification> implements VerificationDao {

    public VerificationDaoHibernate() {
        super();
        setClass(Verification.class);
    }

}
