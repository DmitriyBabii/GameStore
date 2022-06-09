package services;

import models.enums.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ServiceHibernate implements AutoCloseable {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;

    public ServiceHibernate() {
        createAuthorizedClientTable();
        createStorekeeperTable();
        createCourierTable();
        createManagerTable();
        createProductTable();
        createStorageTable();
        createRatingTable();
        createOrderTable();
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
        open();

        StringBuilder stringBuilder = new StringBuilder();
        for (EAuthClient client : EAuthClient.values()) {
            stringBuilder.append(client.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();

        closeSession();
    }

    private void createStorekeeperTable() {
        open();

        StringBuilder stringBuilder = new StringBuilder();
        for (EStorekeeper storekeeper : EStorekeeper.values()) {
            stringBuilder.append(storekeeper.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();

        closeSession();
    }

    private void createCourierTable() {
        open();

        StringBuilder stringBuilder = new StringBuilder();
        for (ECourier courier : ECourier.values()) {
            stringBuilder.append(courier.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();

        closeSession();
    }

    private void createManagerTable() {
        open();

        StringBuilder stringBuilder = new StringBuilder();
        for (EManager manager : EManager.values()) {
            stringBuilder.append(manager.getQuery());
        }

        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
        closeSession();
    }

    private void createProductTable(){
        open();

        StringBuilder stringBuilder = new StringBuilder();
        for (EProduct product : EProduct.values()) {
            stringBuilder.append(product.getQuery());
        }

        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
        closeSession();
    }

    private void createStorageTable(){
        open();

        StringBuilder stringBuilder = new StringBuilder();
        for (EStorage storage : EStorage.values()) {
            stringBuilder.append(storage.getQuery());
        }

        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
        closeSession();
    }

    private void createRatingTable(){
        open();

        StringBuilder stringBuilder = new StringBuilder();
        for (ERating storage : ERating.values()) {
            stringBuilder.append(storage.getQuery());
        }

        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
        closeSession();
    }

    private void createOrderTable(){
        open();

        StringBuilder stringBuilder = new StringBuilder();
        for (EOrder storage : EOrder.values()) {
            stringBuilder.append(storage.getQuery());
        }

        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
        closeSession();
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
