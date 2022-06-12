package services.entity;

import intarfaces.Entity;
import intarfaces.EntityRealize;
import models.Product;
import models.Storage;
import models.enums.EProduct;
import models.enums.EStorage;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

import javax.management.Query;

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
    protected NativeQuery setAllParams(NativeQuery query, Entity entity) {
        Storage storage = (Storage) entity;
        return query.setParameter(EStorage.id_storage.toString(), storage.getId())
                .setParameter(EStorage.id_product_fk.toString(), storage.getProduct().getId())
                .setParameter(EStorage.quantity.toString(), storage.getQuantity());
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
}
