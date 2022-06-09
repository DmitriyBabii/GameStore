package services;

import models.enums.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ServiceHibernate implements AutoCloseable {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;

    public ServiceHibernate() {
        open();
        createAuthorizedClientTable();
        createStorekeeperTable();
        createCourierTable();
        createManagerTable();
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

    private void createAuthorizedClientTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (EAuthClient client : EAuthClient.values()) {
            stringBuilder.append(client.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private void createStorekeeperTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (EStorekeeper storekeeper : EStorekeeper.values()) {
            stringBuilder.append(storekeeper.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private void createCourierTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ECourier courier : ECourier.values()) {
            stringBuilder.append(courier.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private void createManagerTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (EManager manager : EManager.values()) {
            stringBuilder.append(manager.getQuery());
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
