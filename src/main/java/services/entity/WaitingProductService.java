package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Product;
import models.WaitingProduct;
import models.enums.EProduct;
import models.enums.EWaitingProduct;
import services.CriterionService;
import services.ServiceHibernate;

import java.util.ArrayList;
import java.util.List;

public class WaitingProductService extends EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (EWaitingProduct waitingProduct : EWaitingProduct.values()) {
            stringBuilder.append(waitingProduct.getQuery());
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
            WaitingProduct waitingProduct = (WaitingProduct) value;
            ServiceHibernate.getSession().createSQLQuery(getInsertQuery())
                    .setParameter(EWaitingProduct.id_product_fk.toString(), waitingProduct.getProduct().getId())
                    .setParameter(EWaitingProduct.quantity.toString(), waitingProduct.getQuantity())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void update(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            WaitingProduct storage = (WaitingProduct) value;
            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(EWaitingProduct.id_product_fk.toString(), storage.getProduct().getId())
                    .setParameter(EWaitingProduct.quantity.toString(), storage.getQuantity())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            WaitingProduct storage = (WaitingProduct) value;
            ServiceHibernate.getSession()
                    .createSQLQuery(getDeleteQuery())
                    .setParameter(EWaitingProduct.id_product_fk.toString(), storage.getProduct().getId())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    protected String getColumns() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EWaitingProduct s : EWaitingProduct.values()) {
            sb.append(s)
                    .append((++count < EWaitingProduct.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getParams() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EWaitingProduct s : EWaitingProduct.values()) {
            sb.append(":").append(s)
                    .append((++count < EWaitingProduct.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO game_shop.waiting_product (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE game_shop.waiting_product SET ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i])
                    .append("=")
                    .append(params[i])
                    .append((i < columns.length - 1) ? ", " : " ");
        }
        sb.append("WHERE ")
                .append(columns[EWaitingProduct.id_product_fk.ordinal()])
                .append("=")
                .append(params[EWaitingProduct.id_product_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM game_shop.waiting_product ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[EWaitingProduct.id_product_fk.ordinal()])
                .append("=")
                .append(params[EWaitingProduct.id_product_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM game_shop.waiting_product");
        if (criterionList.size() == 0) {
            return sb.toString();
        }
        sb.append(" WHERE ");
        return useCriterion(sb, criterionList);
    }

    @Override
    protected List<WaitingProduct> getEntities(List<Object[]> resultList) {
        ProductService ps = new ProductService();
        CriterionService cs = new CriterionService();
        List<WaitingProduct> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            cs.addCriterion(EProduct.id_product, o[EWaitingProduct.id_product_fk.ordinal()]);
            productList.add(
                    WaitingProduct.builder()
                            .product((Product) ps.select(cs.getCriterionList()).get(0))
                            .quantity((Integer) o[EWaitingProduct.quantity.ordinal()])
                            .build()
            );
        }
        return productList;
    }
}
