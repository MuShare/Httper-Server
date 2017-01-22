package org.fczm.httper.dao.impl;

import org.fczm.common.hibernate.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.UserDao;
import org.fczm.httper.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public class UserDaoHibernate extends PageHibernateDaoSupport<User> implements UserDao {

    public UserDaoHibernate() {
        super();
        setClass(User.class);
    }

    public User getByIdentifierWithType(String identifier, String type) {
        String hql = "from User where identifier = ? and type = ?";
        List<User> users = (List<User>)getHibernateTemplate().find(hql, identifier, type);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

}
