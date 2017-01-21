package org.fczm.httper.dao.impl;

import org.fczm.common.hibernate.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.RequestDao;
import org.fczm.httper.domain.Request;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository
public class RequestDaoHibernate extends PageHibernateDaoSupport<Request> implements RequestDao {

    public RequestDaoHibernate() {
        super();
        setClass(Request.class);
    }

    public int getMaxRevision() {
        final String hql = "select max(revision) from Request";
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                return (Integer) query.uniqueResult();
            }
        });
    }

}
