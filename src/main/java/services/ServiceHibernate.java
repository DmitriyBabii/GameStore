package services;

import models.Product;
import models.Storage;
import models.Text;
import models.enums.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import services.entity.OrderService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ServiceHibernate {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;

    private ServiceHibernate() {

    }

    private static void createAuthorizedUserTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (EUser client : EUser.values()) {
            stringBuilder.append(client.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private static void createProductTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (EProduct product : EProduct.values()) {
            stringBuilder.append(product.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private static void createStorageTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (EStorage storage : EStorage.values()) {
            stringBuilder.append(storage.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private static void createRatingTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ERating storage : ERating.values()) {
            stringBuilder.append(storage.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private static void createOrderTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (EOrder storage : EOrder.values()) {
            stringBuilder.append(storage.getQuery());
        }
        session.createSQLQuery(stringBuilder.toString()).executeUpdate();
    }

    private static void initTables() {
        Text text = new Text(
                "dadsasd"
        );
        Product product = new Product("das", Date.valueOf(LocalDate.now()), text, 18, 1200);
        Storage storage = new Storage(product, 10);
        String sql = "INSERT INTO gameshop.product " +
                "(id_product,name,date_of_release,destination,age_limit,price) " +
                "VALUES(:a,:b,:c,:d,:f,:g);";
        Query query = session.createSQLQuery(sql);
        query.setParameter("a", product.getId());
        query.setParameter("b", product.getName());
        query.setParameter("c", product.getDateOfRelease());
        query.setParameter("d", product.getDescription());
        query.setParameter("f", product.getAgeLimit());
        query.setParameter("g", product.getPrice());
        query.executeUpdate();

        Query q = session.createSQLQuery(
                "INSERT INTO gameshop.storage " +
                        "(id_storage,id_product_fk,quantity) " +
                        "VALUES(:a,:b,:c);"
        );
        q.setParameter("a", storage.getId());
        q.setParameter("b", storage.getProduct().getId());
        q.setParameter("c", 250);
        q.executeUpdate();

        List<Object[]> list = session.createSQLQuery("SELECT * FROM gameshop.storage").list();

        for (Object[] o : list) {
            for (int i = 0; i < 3; i++) {
                System.out.println(o[i]);
            }
        }
        OrderService orderService = new OrderService();
        //orderService.save(product);
        //Storage storage1 = (Storage) product;
    }

    public static void start() {
        open();
        createAuthorizedUserTable();
        createProductTable();
        createStorageTable();
        createRatingTable();
        createOrderTable();
        //initTables();
        close();
    }

    public static void open() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public static void close() {
        session.getTransaction().commit();
        session.close();
    }

    public static Session getSession() {
        return session;
    }
}
