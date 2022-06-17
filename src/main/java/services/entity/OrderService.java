package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Order;
import models.Product;
import models.enums.EOrder;
import models.enums.EProduct;
import models.enums.EProductInOrder;
import models.enums.EUser;
import models.figures.Client;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;
import org.hibernate.query.NativeQuery;
import services.CriterionService;
import services.ParseAgeLimit;
import services.ServiceHibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public final class OrderService extends EntityService {

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
            ServiceHibernate.getSession().createSQLQuery(getInsertQuery())
                    .setParameter(EOrder.id_order.toString(), order.getId())
                    .setParameter(EOrder.id_user_manager_fk.toString(), order.getManager().getId())
                    .setParameter(EOrder.id_user_storekeeper_fk.toString(), order.getStorekeeper().getId())
                    .setParameter(EOrder.id_user_courier_fk.toString(), order.getCourier().getId())
                    .setParameter(EOrder.id_user_client_fk.toString(), order.getClient().getId())
                    //.setParameter(EOrder.id_product_fk.toString(), order.getProduct().getId())
                    .setParameter(EOrder.price.toString(), order.getPrice())
                    .setParameter(EOrder.start_date.toString(), order.getStartOrder())
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
            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(EOrder.id_order.toString(), order.getId())
                    .setParameter(EOrder.id_user_manager_fk.toString(), order.getManager().getId())
                    .setParameter(EOrder.id_user_storekeeper_fk.toString(), order.getStorekeeper().getId())
                    .setParameter(EOrder.id_user_courier_fk.toString(), order.getCourier().getId())
                    .setParameter(EOrder.id_user_client_fk.toString(), order.getClient().getId())
                    //.setParameter(EOrder.id_product_fk.toString(), order.getProduct().getId())
                    .setParameter(EOrder.price.toString(), order.getPrice())
                    .setParameter(EOrder.start_date.toString(), order.getStartOrder())
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
            for (Product p : order.getProducts()) {
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

    public List<Order> select(List<Criterion> criterionList) {
        ServiceHibernate.open();
        NativeQuery query = ServiceHibernate.getSession().createSQLQuery(getSelectQuery(criterionList));
        for (Criterion criterion : criterionList) {
            query.setParameter(criterion.getParameter().toString(), criterion.getValue());
        }
        List<Object[]> resultList = query.list();
        ServiceHibernate.close();

        return getOrders(resultList);
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
        return "INSERT INTO gameshop.order (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    protected String getInsertQueryForProducts() {
        return "INSERT INTO gameshop.product_in_order (" + getColumnsForProducts() +
                ") VALUES(" +
                getParamsForProducts() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE gameshop.order SET ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i])
                    .append("=")
                    .append(params[i])
                    .append((i < columns.length - 1) ? ", " : " ");
        }
        sb.append("WHERE ")
                .append(columns[0])
                .append("=")
                .append(params[0]);
        return sb.toString();
    }

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM gameshop.order ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[0])
                .append("=")
                .append(params[0]);
        return sb.toString();
    }

    protected String getDeleteQueryForProducts() {
        StringBuilder sb = new StringBuilder("DELETE FROM gameshop.product_in_order ");

        String[] columns = getColumnsForProducts().split(",");
        String[] params = getParamsForProducts().split(",");

        sb.append("WHERE ")
                .append(columns[0])
                .append("=")
                .append(params[0]);
        return sb.toString();
    }

    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM gameshop.order WHERE ");
        for (int i = 0; i < criterionList.size(); i++) {
            sb.append(criterionList.get(i).getParameter())
                    .append("=:")
                    .append(criterionList.get(i).getParameter())
                    .append((i + 1) < criterionList.size() ? " AND " : "");
        }
        return sb.toString();
    }

    protected List<Order> getOrders(List<Object[]> resultList) {
        UserService us = new UserService();
        CriterionService cClient = new CriterionService();
        CriterionService cManager = new CriterionService();
        CriterionService cStorekeeper = new CriterionService();
        CriterionService cCourier = new CriterionService();

        List<Order> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            cClient.addCriterion(EUser.id_user, (String) o[1]);
            cManager.addCriterion(EUser.id_user, (String) o[2]);
            cStorekeeper.addCriterion(EUser.id_user, (String) o[3]);
            cCourier.addCriterion(EUser.id_user, (String) o[4]);

            productList.add(
                    Order.builder()
                            .id((String) o[0])
                            .client((Client) us.select(cClient.getCriterionList()).get(0))
                            .manager((Manager) us.select(cManager.getCriterionList()).get(0))
                            .storekeeper((Storekeeper) us.select(cStorekeeper.getCriterionList()).get(0))
                            .courier((Courier) us.select(cCourier.getCriterionList()).get(0))
                            .price((Double) o[5])
                            .startOrder((Date) o[6])
                            .endDateStorekeeper((Date) o[7])
                            .endDateCourier((Date) o[8])
                            .build()
            );
        }
        return productList;
    }
}
