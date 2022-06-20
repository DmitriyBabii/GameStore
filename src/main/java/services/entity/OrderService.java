package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Order;
import models.Product;
import models.enums.EOrder;
import models.enums.EProductInOrder;
import models.enums.EUser;
import models.figures.Client;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;
import services.CriterionService;
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
            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(EOrder.id_order.toString(), order.getId())
                    .setParameter(EOrder.id_user_manager_fk.toString(), order.getManager().getId())
                    .setParameter(EOrder.id_user_storekeeper_fk.toString(), order.getStorekeeper().getId())
                    .setParameter(EOrder.id_user_courier_fk.toString(), order.getCourier().getId())
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
        StringBuilder sb = new StringBuilder("SELECT * FROM game_shop.order WHERE ");
        return useCriterion(sb, criterionList);
    }

    @Override
    protected List<Order> getEntities(List<Object[]> resultList) {
        UserService us = new UserService();
        CriterionService cClient = new CriterionService();
        CriterionService cManager = new CriterionService();
        CriterionService cStorekeeper = new CriterionService();
        CriterionService cCourier = new CriterionService();

        List<Order> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            cClient.addCriterion(EUser.id_user, o[EOrder.id_user_client_fk.ordinal()]);
            cManager.addCriterion(EUser.id_user, o[EOrder.id_user_manager_fk.ordinal()]);
            cStorekeeper.addCriterion(EUser.id_user, o[EOrder.id_user_storekeeper_fk.ordinal()]);
            cCourier.addCriterion(EUser.id_user, o[EOrder.id_user_courier_fk.ordinal()]);

            productList.add(
                    Order.builder()
                            .id((String) o[EOrder.id_order.ordinal()])
                            .client((Client) us.select(cClient.getCriterionList()).get(0))
                            .manager((Manager) us.select(cManager.getCriterionList()).get(0))
                            .storekeeper((Storekeeper) us.select(cStorekeeper.getCriterionList()).get(0))
                            .courier((Courier) us.select(cCourier.getCriterionList()).get(0))
                            .price((Double) o[EOrder.price.ordinal()])
                            .startOrder((Date) o[EOrder.start_date.ordinal()])
                            .endDateManager((Date) o[EOrder.end_date_manager.ordinal()])
                            .endDateStorekeeper((Date) o[EOrder.end_date_storekeeper.ordinal()])
                            .endDateCourier((Date) o[EOrder.end_date_courier.ordinal()])
                            .build()
            );
        }
        return productList;
    }
}
