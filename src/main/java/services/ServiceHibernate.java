package services;

import intarfaces.EntityService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import services.entity.*;

public class ServiceHibernate {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;

    private ServiceHibernate() {

    }

    public static void open() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public static void close() {
        session.getTransaction().commit();
        session.close();
    }

    public static void start() {
        EntityService[] services = {
                new UserService(),
                new ProductService(),
                new StorageService(),
                new RatingService(),
                new OrderService()
        };
        for (EntityService service : services) {
            service.createTable();
        }
    }

    public static Session getSession() {
        return session;
    }
}
