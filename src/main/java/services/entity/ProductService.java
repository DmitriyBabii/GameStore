package services.entity;

import intarfaces.Entity;
import intarfaces.EntityRealize;
import models.Product;
import models.enums.EProduct;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

import javax.management.Query;

public final class ProductService extends EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (EProduct product : EProduct.values()) {
            stringBuilder.append(product.getQuery());
        }
        ServiceHibernate.getSession()
                .createSQLQuery(stringBuilder.toString())
                .executeUpdate();
        ServiceHibernate.close();
    }

    @Override
    public void save(Entity entity) {
        ServiceHibernate.open();
        NativeQuery query = ServiceHibernate
                .getSession()
                .createSQLQuery(getInsertQuery());
        setAllParams(query, entity).executeUpdate();
        ServiceHibernate.close();
    }

    @Override
    public void update(Entity entity) {
        ServiceHibernate.open();
        NativeQuery query = ServiceHibernate
                .getSession()
                .createSQLQuery(getUpdateQuery());
        setAllParams(query, entity).executeUpdate();
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity entity) {

    }

    @Override
    protected String getColumns() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EProduct p : EProduct.values()) {
            sb.append(p)
                    .append((++count < EProduct.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getParams() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EProduct p : EProduct.values()) {
            sb.append(":").append(p)
                    .append((++count < EProduct.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected NativeQuery setAllParams(NativeQuery query, Entity entity) {
        Product product = (Product) entity;
        return query.setParameter(EProduct.id_product.toString(), product.getId())
                .setParameter(EProduct.name.toString(), product.getName())
                .setParameter(EProduct.date_of_release.toString(), product.getDateOfRelease())
                .setParameter(EProduct.destination.toString(), product.getDescription())
                .setParameter(EProduct.age_limit.toString(), product.getAgeLimit())
                .setParameter(EProduct.price.toString(), product.getPrice());
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO gameshop.product (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE gameshop.product SET ");

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
}
