package org.fczm.httper.dao;

import org.fczm.common.hibernate.support.CrudDao;
import org.fczm.httper.domain.Request;

public interface RequestDao extends CrudDao<Request> {

    /**
     * Get the max revision number from all requests.
     * @return
     */
    int getMaxRevision();

}
