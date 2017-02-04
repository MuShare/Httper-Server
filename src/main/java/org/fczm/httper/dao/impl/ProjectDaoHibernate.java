package org.fczm.httper.dao.impl;

import org.fczm.common.hibernate.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.ProjectDao;
import org.fczm.httper.domain.Project;
import org.fczm.httper.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDaoHibernate extends PageHibernateDaoSupport<Project> implements ProjectDao {

    public ProjectDaoHibernate () {
        super();
        setClass(Project.class);
    }

    public int getMaxRevision(final User user) {
        final String hql = "select max(revision) from Project where user = ?";
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
}
