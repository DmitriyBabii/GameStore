package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Product;
import models.Storage;
import models.enums.EProduct;
import models.enums.EStorage;
import org.hibernate.query.NativeQuery;
import services.CriterionService;
import services.ParseAgeLimit;
import services.ServiceHibernate;

import java.sql.Date;
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
                    .setParameter(EStorage.id_storage.toString(), storage.getId())
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
                    .setParameter(EStorage.id_storage.toString(), storage.getId())
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
                    .setParameter(EStorage.id_storage.toString(), storage.getId())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    public List<Storage> select(List<Criterion> criterionList) {
        ServiceHibernate.open();
        NativeQuery query = ServiceHibernate.getSession().createSQLQuery(getSelectQuery(criterionList));
        for (Criterion criterion : criterionList) {
            query.setParameter(criterion.getParameter().toString(), criterion.getValue());
        }
        List<Object[]> resultList = query.list();
        ServiceHibernate.close();

        return getStorages(resultList);
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
        return "INSERT INTO gameshop.storage (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE gameshop.storage SET ");

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
        StringBuilder sb = new StringBuilder("DELETE FROM gameshop.storage ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[0])
                .append("=")
                .append(params[0]);
        return sb.toString();
    }

    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM gameshop.storage WHERE ");
        for (int i = 0; i < criterionList.size(); i++) {
            sb.append(criterionList.get(i).getParameter())
                    .append("=:")
                    .append(criterionList.get(i).getParameter())
                    .append((i + 1) < criterionList.size() ? " AND " : "");
        }
        return sb.toString();
    }

    protected List<Storage> getStorages(List<Object[]> resultList) {
        ProductService ps = new ProductService();
        CriterionService cs = new CriterionService();
        List<Storage> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            cs.addCriterion(EProduct.id_product, (String) o[1]);
            productList.add(
                    Storage.builder()
                            .id((String) o[0])
                            .product(ps.select(cs.getCriterionList()).get(0))
                            .quantity((Integer) o[2])
                            .build()
            );
        }
        return productList;
    }
}
