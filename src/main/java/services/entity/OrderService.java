package services.entity;

import intarfaces.Entity;
import models.Order;
import models.enums.EOrder;
import services.ServiceHibernate;

public final class OrderService extends EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (EOrder order : EOrder.values()) {
            stringBuilder.append(order.getQuery());
        }
        ServiceHibernate.getSession()
                .createSQLQuery(stringBuilder.toString())
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
                    .setParameter(EOrder.id_product_fk.toString(), order.getProduct().getId())
                    .setParameter(EOrder.price.toString(), order.getPrice())
                    .setParameter(EOrder.start_date.toString(), order.getStartOrder())
                    .setParameter(EOrder.end_date_storekeeper.toString(), order.getEndDateStorekeeper())
                    .setParameter(EOrder.end_date_courier.toString(), order.getEndDateCourier())
                    .executeUpdate();
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
                    .setParameter(EOrder.id_product_fk.toString(), order.getProduct().getId())
                    .setParameter(EOrder.price.toString(), order.getPrice())
                    .setParameter(EOrder.start_date.toString(), order.getStartOrder())
                    .setParameter(EOrder.end_date_storekeeper.toString(), order.getEndDateStorekeeper())
                    .setParameter(EOrder.end_date_courier.toString(), order.getEndDateCourier())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Order order = (Order) value;
            ServiceHibernate.getSession()
                    .createSQLQuery(getDeleteQuery())
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

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO gameshop.order (" + getColumns() +
                ") VALUES(" +
                getParams() +
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
}
