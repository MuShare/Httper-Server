package org.fczm.httper.dao.impl;

import org.fczm.common.hibernate.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.ProjectDao;
import org.fczm.httper.domain.Project;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDaoHibernate extends PageHibernateDaoSupport<Project> implements ProjectDao {

    public ProjectDaoHibernate () {
        super();
        setClass(Project.class);
    }

}
