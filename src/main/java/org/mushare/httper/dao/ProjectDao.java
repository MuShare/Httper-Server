package org.mushare.httper.dao;

import org.mushare.common.hibernate.support.CrudDao;
import org.mushare.httper.domain.Project;
import org.mushare.httper.domain.User;

import java.util.List;

public interface ProjectDao extends CrudDao<Project> {

    /**
     * Get max revision of project entities.
     * @param user
     * @return
     */
    int getMaxRevision(User user);

    /**
     * Find updated projects by revision and user.
     * @param revision
     * @param user
     * @return
     */
    List<Project> findUpdatedByRevision(Integer revision, User user);
}
