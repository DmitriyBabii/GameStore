package services;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory
            = configureSessionFactory();

    private HibernateUtil(){

    }

    private static SessionFactory configureSessionFactory()
            throws HibernateException {
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
