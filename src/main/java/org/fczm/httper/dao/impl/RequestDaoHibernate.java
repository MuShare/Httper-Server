package org.fczm.httper.dao.impl;

import org.fczm.common.hibernate.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.RequestDao;
import org.fczm.httper.domain.Request;
import org.fczm.httper.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestDaoHibernate extends PageHibernateDaoSupport<Request> implements RequestDao {

    public RequestDaoHibernate() {
        super();
        setClass(Request.class);
    }

    public int getMaxRevision(final User user) {
        final String hql = "select max(revision) from Request where user = ?";
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0, user);
                Object result = query.uniqueResult();
                if (result == null) {
                    return 0;
                }
                return (Integer) result;
            }
        });
    }

    public List<Request> findUpdatedByRevision(Integer revision, User user) {
        String hql = "from Request where user = ? and revision > ? order by revision";
        return (List<Request>) getHibernateTemplate().find(hql, user, revision);
    }

}
