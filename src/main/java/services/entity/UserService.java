package services.entity;

import intarfaces.Entity;
import intarfaces.EntityRealize;
import models.Product;
import models.enums.EProduct;
import models.enums.EUser;
import models.figures.AuthorizedUser;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

import javax.management.Query;
import java.sql.Date;
import java.time.LocalDate;

public class UserService extends EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (EUser client : EUser.values()) {
            stringBuilder.append(client.getQuery());
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
        for (EUser s : EUser.values()) {
            sb.append(s)
                    .append((++count < EUser.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getParams() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (EUser s : EUser.values()) {
            sb.append(":").append(s)
                    .append((++count < EUser.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected NativeQuery setAllParams(NativeQuery query, Entity entity) {
        AuthorizedUser user = (AuthorizedUser) entity;
        return query.setParameter(EUser.id_user.toString(), user.getId())
                .setParameter(EUser.name.toString(), user.getName())
                .setParameter(EUser.last_name.toString(), user.getLastName())
                .setParameter(EUser.username.toString(), user.getUsername())
                .setParameter(EUser.password.toString(), user.getPassword())
                .setParameter(EUser.phone_number.toString(), user.getPhoneNumber())
                .setParameter(EUser.email.toString(), user.getEmail())
                .setParameter(EUser.date_of_birth.toString(), Date.valueOf(LocalDate.now()))
                .setParameter(EUser.role.toString(), user.getRole());
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO gameshop.user (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE gameshop.user SET ");

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
