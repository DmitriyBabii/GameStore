package services.entity;

import intarfaces.Entity;
import intarfaces.EntityService;
import models.Order;
import models.enums.EOrder;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

public class OrderService implements EntityService {

    @Override
    public void save(Entity entity) {
        try {
            Order order = (Order) entity;
            StringBuilder sb = new StringBuilder("INSERT INTO gameshop.order ");
            sb.append(getColumns())
                    .append(" VALUES")
                    .append(getParams())
                    .append(";");
            System.out.println(sb);

            ServiceHibernate.open();
            NativeQuery query = ServiceHibernate.getSession().createSQLQuery(sb.toString());
            query.setParameter(EOrder.id_order.toString(), order.getId());
            query.setParameter(EOrder.id_user_manager_fk.toString(), order.getManager().getId());
            query.setParameter(EOrder.id_user_storekeeper_fk.toString(), order.getStorekeeper().getId());
            query.setParameter(EOrder.id_user_courier_fk.toString(), order.getCourier().getId());
            query.setParameter(EOrder.id_user_client_fk.toString(), order.getClient().getId());
            query.setParameter(EOrder.id_product_fk.toString(), order.getProduct().getId());
            query.setParameter(EOrder.price.toString(), order.getPrice());
            query.setParameter(EOrder.start_date.toString(), order.getStartOrder());
            query.setParameter(EOrder.end_date_storekeeper.toString(), order.getEndDateStorekeeper());
            query.setParameter(EOrder.end_date_courier.toString(), order.getEndDateCourier());
            query.executeUpdate();
            ServiceHibernate.close();

        } catch (ClassCastException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void delete(Entity entity) {

    }

    @Override
    public String getColumns() {
        StringBuilder sb = new StringBuilder("(");
        int count = 0;
        for (EOrder p : EOrder.values()) {
            sb.append(p)
                    .append((++count < EOrder.values().length) ? "," : ")");
        }
        return sb.toString();
    }

    @Override
    public String getParams() {
        StringBuilder sb = new StringBuilder("(");
        int count = 0;
        for (EOrder p : EOrder.values()) {
            sb.append(":").append(p)
                    .append((++count < EOrder.values().length) ? "," : ")");
        }
        return sb.toString();
    }
}
