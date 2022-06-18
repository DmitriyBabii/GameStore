package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.ReservedProduct;
import models.enums.EProduct;
import models.enums.EReservedProduct;
import org.hibernate.query.NativeQuery;
import services.CriterionService;
import services.ServiceHibernate;

import java.util.ArrayList;
import java.util.List;

public class ReservedProductService extends EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (EReservedProduct ReservedProduct : EReservedProduct.values()) {
            stringBuilder.append(ReservedProduct.getQuery());
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
            ReservedProduct reservedProduct = (ReservedProduct) value;
            ServiceHibernate.getSession().createSQLQuery(getInsertQuery())
                    .setParameter(EReservedProduct.id_product_fk.toString(), reservedProduct.getProduct().getId())
                    .setParameter(EReservedProduct.quantity.toString(), reservedProduct.getQuantity())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void update(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            ReservedProduct reservedProduct = (ReservedProduct) value;
            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(EReservedProduct.id_product_fk.toString(), reservedProduct.getProduct().getId())
                    .setParameter(EReservedProduct.quantity.toString(), reservedProduct.getQuantity())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            ReservedProduct reservedProduct = (ReservedProduct) value;
            ServiceHibernate.getSession()
                    .createSQLQuery(getDeleteQuery())
                    .setParameter(EReservedProduct.id_product_fk.toString(), reservedProduct.getProduct().getId())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public List<ReservedProduct> select(List<Criterion> criterionList) {
        ServiceHibernate.open();
        @SuppressWarnings("rawtypes")
        NativeQuery query = ServiceHibernate.getSession().createSQLQuery(getSelectQuery(criterionList));
        for (Criterion criterion : criterionList) {
            if (criterion.getValue() != null) {
                query.setParameter(criterion.getParameter().toString(), criterion.getValue());
            }
        }
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.list();
        ServiceHibernate.close();

        return getEntities(resultList);
    }

    @Override
    protected String getColumns() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EReservedProduct s : EReservedProduct.values()) {
            sb.append(s)
                    .append((++count < EReservedProduct.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getParams() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EReservedProduct s : EReservedProduct.values()) {
            sb.append(":").append(s)
                    .append((++count < EReservedProduct.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO game_shop.reserved_product (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE game_shop.reserved_product SET ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i])
                    .append("=")
                    .append(params[i])
                    .append((i < columns.length - 1) ? ", " : " ");
        }
        sb.append("WHERE ")
                .append(columns[EReservedProduct.id_product_fk.ordinal()])
                .append("=")
                .append(params[EReservedProduct.id_product_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM game_shop.reserved_product ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[EReservedProduct.id_product_fk.ordinal()])
                .append("=")
                .append(params[EReservedProduct.id_product_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM game_shop.reserved_product WHERE ");
        for (int i = 0; i < criterionList.size(); i++) {
            Object o = criterionList.get(i).getValue();
            sb.append(criterionList.get(i).getParameter())
                    .append(criterionList.get(i).getOperator().getQuery())
                    .append((o != null) ? (":" + criterionList.get(i).getParameter()) : "")
                    .append((i + 1) < criterionList.size() ? " AND " : "");
        }
        return sb.toString();
    }

    @Override
    protected List<ReservedProduct> getEntities(List<Object[]> resultList) {
        ProductService ps = new ProductService();
        CriterionService cs = new CriterionService();
        List<ReservedProduct> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            cs.addCriterion(EProduct.id_product, o[EReservedProduct.id_product_fk.ordinal()]);
            productList.add(
                    ReservedProduct.builder()
                            .product(ps.select(cs.getCriterionList()).get(0))
                            .quantity((Integer) o[EReservedProduct.quantity.ordinal()])
                            .build()
            );
        }
        return productList;
    }
}
