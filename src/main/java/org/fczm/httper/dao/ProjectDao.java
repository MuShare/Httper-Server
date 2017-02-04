package org.fczm.httper.dao;

import org.fczm.common.hibernate.support.CrudDao;
import org.fczm.httper.domain.Project;
import org.fczm.httper.domain.User;

public interface ProjectDao extends CrudDao<Project> {

    /**
     * Get max revision of project entities.
     * @param user
     * @return
     */
    int getMaxRevision(User user);
}
