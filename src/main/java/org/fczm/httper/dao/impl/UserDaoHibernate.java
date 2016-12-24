package org.fczm.httper.dao.impl;

import org.fczm.common.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.UserDao;
import org.fczm.httper.domain.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoHibernate extends PageHibernateDaoSupport<User> implements UserDao {

    public UserDaoHibernate() {
        super();
        setClass(User.class);
    }


}
