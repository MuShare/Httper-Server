package org.mushare.httper.dao.impl;

import org.mushare.common.hibernate.support.PageHibernateDaoSupport;
import org.mushare.httper.dao.ProjectDao;
import org.mushare.httper.domain.Project;
import org.mushare.httper.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Project> findUpdatedByRevision(Integer revision, User user) {
        String hql = "from Project where user = ? and revision > ? order by revision";
        return (List<Project>)getHibernateTemplate().find(hql, user, revision);
    }
}
