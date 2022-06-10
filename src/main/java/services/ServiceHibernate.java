package services;

import models.enums.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ServiceHibernate implements AutoCloseable {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;

    public ServiceHibernate() {
        open();
        createAuthorizedUserTable();
        createProductTable();
        createStorageTable();
        createRatingTable();
        createOrderTable();
        closeSession();
    }

    private void open() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    private void closeSession() {
        session.getTransaction().commit();
        session.close();
    }

    private void createAuthorizedUserTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (EUser client : EUser.values()) {
            stringBuilder.append(client.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private void createProductTable(){
        StringBuilder stringBuilder = new StringBuilder();
        for (EProduct product : EProduct.values()) {
            stringBuilder.append(product.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private void createStorageTable(){
        StringBuilder stringBuilder = new StringBuilder();
        for (EStorage storage : EStorage.values()) {
            stringBuilder.append(storage.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private void createRatingTable(){
        StringBuilder stringBuilder = new StringBuilder();
        for (ERating storage : ERating.values()) {
            stringBuilder.append(storage.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private void createOrderTable(){
        StringBuilder stringBuilder = new StringBuilder();
        for (EOrder storage : EOrder.values()) {
            stringBuilder.append(storage.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
