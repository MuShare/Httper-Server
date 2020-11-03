package org.mushare.httper.dao;

import org.mushare.common.hibernate.support.CrudDao;
import org.mushare.httper.domain.Project;
import org.mushare.httper.domain.Request;
import org.mushare.httper.domain.User;

import java.util.List;

public interface RequestDao extends CrudDao<Request> {

    /**
     * Get the max revision number from all requests.
     * @param user
     * @return
     */
    int getMaxRevision(User user);

    /**
     * Find updated revision by revision and user.
     * @param revision
     * @param user
     * @return
     */
    List<Request> findUpdatedByRevision(Integer revision, User user);

    /**
     * Find requests by project.
     * @param project
     * @return
     */
    List<Request> findByProject(Project project);

}
