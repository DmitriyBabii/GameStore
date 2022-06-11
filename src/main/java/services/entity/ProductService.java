package services.entity;

import intarfaces.Entity;
import intarfaces.EntityService;
import models.Product;
import models.enums.EProduct;
import models.enums.ERating;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

public class ProductService implements EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (EProduct product : EProduct.values()) {
            stringBuilder.append(product.getQuery());
        }
        ServiceHibernate.getSession().createSQLQuery(stringBuilder.toString()).executeUpdate();
        ServiceHibernate.close();
    }

    @Override
    public void save(Entity entity) {
        try {
            Product product = (Product) entity;
            StringBuilder sb = new StringBuilder("INSERT INTO gameshop.product ");
            sb.append(getColumns())
                    .append(" VALUES")
                    .append(getParams())
                    .append(";");

            ServiceHibernate.open();
            NativeQuery query = ServiceHibernate.getSession().createSQLQuery(sb.toString());
            query.setParameter(EProduct.id_product.toString(), product.getId());
            query.setParameter(EProduct.name.toString(), product.getName());
            query.setParameter(EProduct.date_of_release.toString(), product.getDateOfRelease());
            query.setParameter(EProduct.destination.toString(), product.getDescription());
            query.setParameter(EProduct.age_limit.toString(), product.getAgeLimit());
            query.setParameter(EProduct.price.toString(), product.getPrice());
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
        for (EProduct p : EProduct.values()) {
            sb.append(p)
                    .append((++count < EProduct.values().length) ? "," : ")");
        }
        return sb.toString();
    }

    @Override
    public String getParams() {
        StringBuilder sb = new StringBuilder("(");
        int count = 0;
        for (EProduct p : EProduct.values()) {
            sb.append(":").append(p)
                    .append((++count < EProduct.values().length) ? "," : ")");
        }
        return sb.toString();
    }
}
