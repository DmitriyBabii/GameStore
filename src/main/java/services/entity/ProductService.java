package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Product;
import models.enums.EProduct;
import services.ParseAgeLimit;
import services.ServiceHibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
        return "INSERT INTO game_shop.product (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE game_shop.product SET ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i])
                    .append("=")
                    .append(params[i])
                    .append((i < columns.length - 1) ? ", " : " ");
        }
        sb.append("WHERE ")
                .append(columns[EProduct.id_product.ordinal()])
                .append("=")
                .append(params[EProduct.id_product.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM game_shop.product ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[EProduct.id_product.ordinal()])
                .append("=")
                .append(params[EProduct.id_product.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM game_shop.product");
        if (criterionList.size() == 0) {
            return sb.toString();
        }
        sb.append(" WHERE ");
        return useCriterion(sb, criterionList);
    }

    @Override
    protected List<Product> getEntities(List<Object[]> resultList) {
        List<Product> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            productList.add(
                    Product.builder()
                            .id((String) o[EProduct.id_product.ordinal()])
                            .name((String) o[EProduct.name.ordinal()])
                            .dateOfRelease((Date) o[EProduct.date_of_release.ordinal()])
                            .description((String) o[EProduct.destination.ordinal()])
                            .ageLimit(ParseAgeLimit.getAgeLimit((Integer) o[EProduct.age_limit.ordinal()]))
                            .price((Double) o[EProduct.price.ordinal()])
                            .build()
            );
        }
        return productList;
    }
}
