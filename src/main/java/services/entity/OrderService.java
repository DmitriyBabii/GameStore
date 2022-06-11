package services.entity;

import intarfaces.Entity;
import intarfaces.EntityService;

public class OrderService implements EntityService {

    @Override
    public void save(Entity entity) {
        /*try {
            Manager manager = new Manager("a", "b", "c", "d",
                    "f", "g", Date.valueOf(LocalDate.now()));
            Storekeeper storekeeper = new Storekeeper("a", "b", "c", "d",
                    "f", "g", Date.valueOf(LocalDate.now()));
            Courier courier = new Courier("a", "b", "c", "d",
                    "f", "g", Date.valueOf(LocalDate.now()));
            Client client = new Client("a", "b", "c", "d",
                    "f", "g", Date.valueOf(LocalDate.now()));
            Text text = new Text("Some text");
            Product product = new Product("a", Date.valueOf(LocalDate.now()), text, 18, 200);
            Order order = new Order(manager, storekeeper, courier, client, product, 2000.2);
            //Order order = (Order) entity;
            StringBuilder sb = new StringBuilder("INSERT INTO gameshop.order ");
            sb.append(
                    Arrays.toString(EOrder.values())
                            .replace("[", "(")
                            .replace("]", ")")
            );
            sb.append(" VALUES(")
                    .append(order.getId()).append(",")
                    .append(order.getUser().getId()).append(",")
                    .append(order.getManager().getId()).append(",")
                    .append(order.getStorekeeper().getId()).append(",")
                    .append(order.getCourier().getId()).append(",")
                    .append(order.getProduct().getId()).append(",")
                    .append(order.getPrice()).append(",")
                    .append(order.getStartOrder()).append(",")
                    .append(order.getEndDateStorekeeper()).append(",")
                    .append(order.getEndDateCourier()).append(");");

            ServiceHibernate.open();

            Query query = ServiceHibernate.getSession().createSQLQuery(sb.toString());
            //query.executeUpdate();
            System.out.println(sb);

            ServiceHibernate.close();

        } catch (ClassCastException ex) {
            System.out.println(ex.getMessage());
        }*/
    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void delete(Entity entity) {

    }

    @Override
    public String getColumns() {
        return null;
    }

    @Override
    public String getParams() {
        return null;
    }
}
