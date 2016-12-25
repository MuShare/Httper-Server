package org.fczm.common.support;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

public class AutoFlushOpenSessionInViewFilter extends OpenSessionInViewFilter {

    @Override
    protected Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
        try {
            Session session = sessionFactory.openSession();
            session.setFlushMode(FlushMode.COMMIT); // This line changes the default behavior
            return session;
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Could not open Hibernate Session", e);
        }
    }

}
