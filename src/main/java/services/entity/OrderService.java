package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Order;
import models.Product;
import models.enums.*;
import models.figures.Client;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;
import org.hibernate.query.NativeQuery;
import services.CriterionService;
import services.ServiceHibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public final class OrderService extends EntityService {
    private static final CriterionService cs = new CriterionService();

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder strOrder = new StringBuilder();
        for (EOrder order : EOrder.values()) {
            strOrder.append(order.getQuery());
        }
        ServiceHibernate.getSession()
                .createSQLQuery(strOrder.toString())
                .executeUpdate();

        StringBuilder strProductInOrder = new StringBuilder();
        for (EProductInOrder order : EProductInOrder.values()) {
            strProductInOrder.append(order.getQuery());
        }
        System.out.println(strProductInOrder);
        ServiceHibernate.getSession().createSQLQuery(strProductInOrder.toString())
                .executeUpdate();
        ServiceHibernate.close();
    }

    @Override
    public void save(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Order order = (Order) value;
            Manager manager = order.getManager();
            Storekeeper storekeeper = order.getStorekeeper();
            Courier courier = order.getCourier();
            ServiceHibernate.getSession().createSQLQuery(getInsertQuery())
                    .setParameter(EOrder.id_order.toString(), order.getId())
                    .setParameter(EOrder.id_user_manager_fk.toString(), (manager != null) ? manager.getId() : null)
                    .setParameter(EOrder.id_user_storekeeper_fk.toString(), (storekeeper != null) ? storekeeper.getId() : null)
                    .setParameter(EOrder.id_user_courier_fk.toString(), (courier != null) ? courier.getId() : null)
                    .setParameter(EOrder.id_user_client_fk.toString(), order.getClient().getId())
                    .setParameter(EOrder.price.toString(), order.getPrice())
                    .setParameter(EOrder.start_date.toString(), order.getStartOrder())
                    .setParameter(EOrder.end_date_manager.toString(), order.getEndDateManager())
                    .setParameter(EOrder.end_date_storekeeper.toString(), order.getEndDateStorekeeper())
                    .setParameter(EOrder.end_date_courier.toString(), order.getEndDateCourier())
                    .executeUpdate();

            for (Product p : order.getProducts()) {
                ServiceHibernate.getSession().createSQLQuery(getInsertQueryForProducts())
                        .setParameter(EProductInOrder.id_order_fk.toString(), order.getId())
                        .setParameter(EProductInOrder.id_product_fk.toString(), p.getId())
                        .executeUpdate();
            }
        }

        ServiceHibernate.close();
    }

    @Override
    public void update(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Order order = (Order) value;

            Manager manager = order.getManager();
            Storekeeper storekeeper = order.getStorekeeper();
            Courier courier = order.getCourier();

            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(EOrder.id_order.toString(), order.getId())
                    .setParameter(EOrder.id_user_manager_fk.toString(), (manager != null) ? manager.getId() : null)
                    .setParameter(EOrder.id_user_storekeeper_fk.toString(), (storekeeper != null) ? storekeeper.getId() : null)
                    .setParameter(EOrder.id_user_courier_fk.toString(), (courier != null) ? courier.getId() : null)
                    .setParameter(EOrder.id_user_client_fk.toString(), order.getClient().getId())
                    .setParameter(EOrder.price.toString(), order.getPrice())
                    .setParameter(EOrder.start_date.toString(), order.getStartOrder())
                    .setParameter(EOrder.end_date_manager.toString(), order.getEndDateManager())
                    .setParameter(EOrder.end_date_storekeeper.toString(), order.getEndDateStorekeeper())
                    .setParameter(EOrder.end_date_courier.toString(), order.getEndDateCourier())
                    .executeUpdate();
            ServiceHibernate.close();
        }
    }

    @Override
    public void delete(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Order order = (Order) value;
            for (Product ignored : order.getProducts()) {
                ServiceHibernate.getSession().createSQLQuery(getDeleteQueryForProducts())
                        .setParameter(EProductInOrder.id_order_fk.toString(), order.getId())
                        .executeUpdate();
            }

            ServiceHibernate.getSession().createSQLQuery(getDeleteQuery())
                    .setParameter(EOrder.id_order.toString(), order.getId())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    protected String getColumns() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EOrder p : EOrder.values()) {
            sb.append(p)
                    .append((++count < EOrder.values().length) ? "," : "");
        }
        return sb.toString();
    }

    protected String getColumnsForProducts() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EProductInOrder p : EProductInOrder.values()) {
            sb.append(p)
                    .append((++count < EProductInOrder.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getParams() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EOrder p : EOrder.values()) {
            sb.append(":").append(p)
                    .append((++count < EOrder.values().length) ? "," : "");
        }
        return sb.toString();
    }

    protected String getParamsForProducts() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EProductInOrder p : EProductInOrder.values()) {
            sb.append(":").append(p)
                    .append((++count < EProductInOrder.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO game_shop.order (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    protected String getInsertQueryForProducts() {
        return "INSERT INTO game_shop.product_in_order (" + getColumnsForProducts() +
                ") VALUES(" +
                getParamsForProducts() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE game_shop.order SET ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i])
                    .append("=")
                    .append(params[i])
                    .append((i < columns.length - 1) ? ", " : " ");
        }
        sb.append("WHERE ")
                .append(columns[EOrder.id_order.ordinal()])
                .append("=")
                .append(params[EOrder.id_order.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM game_shop.order ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[EOrder.id_order.ordinal()])
                .append("=")
                .append(params[EOrder.id_order.ordinal()]);
        return sb.toString();
    }

    protected String getDeleteQueryForProducts() {
        StringBuilder sb = new StringBuilder("DELETE FROM game_shop.product_in_order ");

        String[] columns = getColumnsForProducts().split(",");
        String[] params = getParamsForProducts().split(",");

        sb.append("WHERE ")
                .append(columns[EProductInOrder.id_order_fk.ordinal()])
                .append("=")
                .append(params[EProductInOrder.id_order_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM game_shop.order");
        if (criterionList.size() == 0) {
            return sb.toString();
        }
        sb.append(" WHERE ");
        return useCriterion(sb, criterionList);
    }

    protected String getSelectQueryForProducts(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM game_shop.product_in_order");
        if (criterionList.size() == 0) {
            return sb.toString();
        }
        sb.append(" WHERE ");
        return useCriterion(sb, criterionList);
    }

    @Override
    protected List<Order> getEntities(List<Object[]> resultList) {
        UserService us = new UserService();

        CriterionService cClient = new CriterionService();
        CriterionService cManager = new CriterionService();
        CriterionService cStorekeeper = new CriterionService();
        CriterionService cCourier = new CriterionService();

        List<Order> orderList = new ArrayList<>();
        for (Object[] o : resultList) {
            cClient.clear();
            cManager.clear();
            cStorekeeper.clear();
            cCourier.clear();
            cClient.addCriterion(EUser.id_user, o[EOrder.id_user_client_fk.ordinal()]);
            cManager.addCriterion(EUser.id_user, o[EOrder.id_user_manager_fk.ordinal()]);
            cStorekeeper.addCriterion(EUser.id_user, o[EOrder.id_user_storekeeper_fk.ordinal()]);
            cCourier.addCriterion(EUser.id_user, o[EOrder.id_user_courier_fk.ordinal()]);

            Manager manager = (o[EOrder.id_user_manager_fk.ordinal()] != null) ?
                    (Manager) us.select(cManager.getCriterionList()).get(0) : null;

            Storekeeper storekeeper = (o[EOrder.id_user_storekeeper_fk.ordinal()] != null) ?
                    (Storekeeper) us.select(cStorekeeper.getCriterionList()).get(0) : null;

            Courier courier = (o[EOrder.id_user_courier_fk.ordinal()] != null) ?
                    (Courier) us.select(cCourier.getCriterionList()).get(0) : null;

            orderList.add(
                    Order.builder()
                            .id((String) o[EOrder.id_order.ordinal()])
                            .client((Client) us.select(cClient.getCriterionList()).get(0))
                            .manager(manager)
                            .storekeeper(storekeeper)
                            .courier(courier)
                            .price((Double) o[EOrder.price.ordinal()])
                            .products(selectProducts((String) o[EOrder.id_order.ordinal()]))
                            .startOrder((Date) o[EOrder.start_date.ordinal()])
                            .endDateManager((Date) o[EOrder.end_date_manager.ordinal()])
                            .endDateStorekeeper((Date) o[EOrder.end_date_storekeeper.ordinal()])
                            .endDateCourier((Date) o[EOrder.end_date_courier.ordinal()])
                            .build()
            );
        }
        return orderList;
    }

    private List<Product> selectProducts(String id) {
        CriterionService cProductInOrder = new CriterionService();
        cProductInOrder.addCriterion(EProductInOrder.id_order_fk, id);

        ServiceHibernate.open();
        @SuppressWarnings("rawtypes")
        NativeQuery query = ServiceHibernate.getSession()
                .createSQLQuery(getSelectQueryForProducts(cProductInOrder.getCriterionList()));
        for (Criterion criterion : cProductInOrder.getCriterionList()) {
            if (criterion.getValue() != null) {
                query.setParameter(criterion.getParameter().toString(), criterion.getValue());
            }
        }
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.list();
        ServiceHibernate.close();
        return getEntityProduct(resultList);
    }

    private List<Product> getEntityProduct(List<Object[]> resultList) {
        ProductService ps = new ProductService();
        CriterionService cProduct = new CriterionService();

        List<Product> products = new ArrayList<>();
        for (Object[] o : resultList) {
            cProduct.clear();
            cProduct.addCriterion(EProduct.id_product, o[EProductInOrder.id_product_fk.ordinal()]);
            products.add((Product) ps.select(cProduct.getCriterionList()).get(0));
        }
        return products;
    }

    public List<Order> getOrdersForUser(String id) {
        cs.clear();
        cs.addCriterion(EOrder.id_user_client_fk, id);
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) select(cs.getCriterionList());
        return orders;
    }

    public List<Order> getOrdersForManager() {
        cs.clear();
        cs.addCriterion(EOrder.end_date_manager, Operator.IS_NULL, null);
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) select(cs.getCriterionList());
        return orders;
    }

    public List<Order> getOrdersForStorekeeper() {
        cs.clear();
        cs.addCriterion(EOrder.end_date_storekeeper, Operator.IS_NULL, null);
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) select(cs.getCriterionList());
        return orders;
    }

    public List<Order> getOrdersForCourier() {
        cs.clear();
        cs.addCriterion(EOrder.end_date_courier, Operator.IS_NULL, null);
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) select(cs.getCriterionList());
        return orders;
    }

    public List<Product> getProducts(String id) {
        cs.clear();
        cs.addCriterion(EOrder.id_order, id);
        Order order = (Order) select(cs.getCriterionList()).get(0);
        System.out.println(order);
        System.out.println(order.getProducts());
        return order.getProducts();
    }

    public Order getOrder(String id) {
        cs.clear();
        cs.addCriterion(EOrder.id_order, id);
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) select(cs.getCriterionList());
        return (orders.size() > 0) ? orders.get(0) : null;
    }
}
