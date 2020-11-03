package org.mushare.httper.dao;

import org.mushare.common.hibernate.support.CrudDao;
import org.mushare.httper.domain.User;

public interface UserDao extends CrudDao<User> {

    /**
     * Get a user by an indentifier with a type
     * @param identifier
     * @param type
     * @return
     */
    User getByIdentifierWithType(String identifier, String type);

}
