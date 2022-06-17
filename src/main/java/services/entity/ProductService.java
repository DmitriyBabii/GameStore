package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Product;
import models.enums.AgeLimit;
import models.enums.EProduct;
import org.hibernate.query.NativeQuery;
import services.ParseAgeLimit;
import services.ServiceHibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

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
    public void save(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Product product = (Product) value;
            ServiceHibernate.getSession().createSQLQuery(getInsertQuery())
                    .setParameter(EProduct.id_product.toString(), product.getId())
                    .setParameter(EProduct.name.toString(), product.getName())
                    .setParameter(EProduct.date_of_release.toString(), product.getDateOfRelease())
                    .setParameter(EProduct.destination.toString(), product.getDescription())
                    .setParameter(EProduct.age_limit.toString(), product.getAgeLimit())
                    .setParameter(EProduct.price.toString(), product.getPrice())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void update(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Product product = (Product) value;
            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(EProduct.id_product.toString(), product.getId())
                    .setParameter(EProduct.name.toString(), product.getName())
                    .setParameter(EProduct.date_of_release.toString(), product.getDateOfRelease())
                    .setParameter(EProduct.destination.toString(), product.getDescription())
                    .setParameter(EProduct.age_limit.toString(), product.getAgeLimit())
                    .setParameter(EProduct.price.toString(), product.getPrice())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Product product = (Product) value;
            ServiceHibernate.getSession().createSQLQuery(getDeleteQuery())
                    .setParameter(EProduct.id_product.toString(), product.getId())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    public List<Product> select(List<Criterion> criterionList) {
        ServiceHibernate.open();
        NativeQuery query = ServiceHibernate.getSession().createSQLQuery(getSelectQuery(criterionList));
        for (Criterion criterion : criterionList) {
            query.setParameter(criterion.getParameter().toString(), criterion.getValue());
        }
        List<Object[]> resultList = query.list();
        ServiceHibernate.close();

        return getProducts(resultList);
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

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM gameshop.product ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[0])
                .append("=")
                .append(params[0]);
        return sb.toString();
    }

    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM gameshop.product WHERE ");
        for (int i = 0; i < criterionList.size(); i++) {
            sb.append(criterionList.get(i).getParameter())
                    .append("=:")
                    .append(criterionList.get(i).getParameter())
                    .append((i + 1) < criterionList.size() ? " AND " : "");
        }
        return sb.toString();
    }

    protected List<Product> getProducts(List<Object[]> resultList) {
        List<Product> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            productList.add(
                    Product.builder()
                            .id((String) o[0])
                            .name((String) o[1])
                            .dateOfRelease((Date) o[2])
                            .description((String) o[3])
                            .ageLimit(ParseAgeLimit.getAgeLimit((Integer) o[4]))
                            .price((Double) o[5])
                            .build()
            );
        }
        return productList;
    }
}
