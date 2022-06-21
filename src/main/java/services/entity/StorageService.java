package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Product;
import models.Storage;
import models.enums.EProduct;
import models.enums.EStorage;
import services.CriterionService;
import services.ServiceHibernate;

import java.util.ArrayList;
import java.util.List;

public final class StorageService extends EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (EStorage storage : EStorage.values()) {
            stringBuilder.append(storage.getQuery());
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
            Storage storage = (Storage) value;
            ServiceHibernate.getSession().createSQLQuery(getInsertQuery())
                    .setParameter(EStorage.id_product_fk.toString(), storage.getProduct().getId())
                    .setParameter(EStorage.quantity.toString(), storage.getQuantity())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void update(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Storage storage = (Storage) value;
            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(EStorage.id_product_fk.toString(), storage.getProduct().getId())
                    .setParameter(EStorage.quantity.toString(), storage.getQuantity())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Storage storage = (Storage) value;
            ServiceHibernate.getSession()
                    .createSQLQuery(getDeleteQuery())
                    .setParameter(EStorage.id_product_fk.toString(), storage.getProduct().getId())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    protected String getColumns() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EStorage s : EStorage.values()) {
            sb.append(s)
                    .append((++count < EStorage.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getParams() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EStorage s : EStorage.values()) {
            sb.append(":").append(s)
                    .append((++count < EStorage.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO game_shop.storage (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE game_shop.storage SET ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i])
                    .append("=")
                    .append(params[i])
                    .append((i < columns.length - 1) ? ", " : " ");
        }
        sb.append("WHERE ")
                .append(columns[EStorage.id_product_fk.ordinal()])
                .append("=")
                .append(params[EStorage.id_product_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM game_shop.storage ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[EStorage.id_product_fk.ordinal()])
                .append("=")
                .append(params[EStorage.id_product_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM game_shop.storage");
        if (criterionList.size() == 0) {
            return sb.toString();
        }
        sb.append(" WHERE ");
        return useCriterion(sb, criterionList);
    }

    @Override
    protected List<Storage> getEntities(List<Object[]> resultList) {
        ProductService ps = new ProductService();
        CriterionService cs = new CriterionService();
        List<Storage> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            cs.addCriterion(EProduct.id_product, o[EStorage.id_product_fk.ordinal()]);
            productList.add(
                    Storage.builder()
                            .product((Product) ps.select(cs.getCriterionList()).get(0))
                            .quantity((Integer) o[EStorage.quantity.ordinal()])
                            .build()
            );
        }
        return productList;
    }
}
