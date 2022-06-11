package services.entity;

import intarfaces.Entity;
import intarfaces.EntityService;
import models.Storage;
import models.enums.EStorage;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

public class StorageService implements EntityService {

    @Override
    public void save(Entity entity) {
        try {
            Storage storage = (Storage) entity;
            StringBuilder sb = new StringBuilder("INSERT INTO gameshop.storage ");
            sb.append(getColumns())
                    .append(" VALUES")
                    .append(getParams())
                    .append(";");
            System.out.println(sb);

            ServiceHibernate.open();
            NativeQuery query = ServiceHibernate.getSession().createSQLQuery(sb.toString());
            query.setParameter(EStorage.id_storage.toString(), storage.getId());
            query.setParameter(EStorage.id_product_fk.toString(), storage.getProduct().getId());
            query.setParameter(EStorage.quantity.toString(), storage.getQuantity());
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
        for (EStorage s : EStorage.values()) {
            sb.append(s)
                    .append((++count < EStorage.values().length) ? "," : ")");
        }
        return sb.toString();
    }

    @Override
    public String getParams() {
        StringBuilder sb = new StringBuilder("(");
        int count = 0;
        for (EStorage s : EStorage.values()) {
            sb.append(":").append(s)
                    .append((++count < EStorage.values().length) ? "," : ")");
        }
        return sb.toString();
    }
}
