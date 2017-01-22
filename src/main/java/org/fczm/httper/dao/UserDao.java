package org.fczm.httper.dao;

import org.fczm.common.hibernate.support.CrudDao;
import org.fczm.httper.domain.User;

public interface UserDao extends CrudDao<User> {

    /**
     * Get a user by an indentifier with a type
     * @param identifier
     * @param type
     * @return
     */
    User getByIdentifierWithType(String identifier, String type);

}
