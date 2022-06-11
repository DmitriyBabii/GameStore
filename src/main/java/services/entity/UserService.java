package services.entity;

import intarfaces.Entity;
import intarfaces.EntityService;
import models.Storage;
import models.enums.EStorage;
import models.enums.EUser;
import models.figures.AuthorizedUser;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

import java.sql.Date;
import java.time.LocalDate;

public class UserService implements EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (EUser client : EUser.values()) {
            stringBuilder.append(client.getQuery());
        }
        ServiceHibernate.getSession().createSQLQuery(stringBuilder.toString()).executeUpdate();
        ServiceHibernate.close();
    }

    @Override
    public void save(Entity entity) {
        try {
            AuthorizedUser user = (AuthorizedUser) entity;
            StringBuilder sb = new StringBuilder("INSERT INTO gameshop.user ");
            sb.append(getColumns())
                    .append(" VALUES")
                    .append(getParams())
                    .append(";");

            ServiceHibernate.open();
            NativeQuery query = ServiceHibernate.getSession().createSQLQuery(sb.toString());
            query.setParameter(EUser.id_user.toString(), user.getId());
            query.setParameter(EUser.name.toString(), user.getName());
            query.setParameter(EUser.last_name.toString(), user.getLastName());
            query.setParameter(EUser.username.toString(), user.getUsername());
            query.setParameter(EUser.password.toString(), user.getPassword());
            query.setParameter(EUser.phone_number.toString(), user.getPhoneNumber());
            query.setParameter(EUser.email.toString(), user.getEmail());
            query.setParameter(EUser.date_of_birth.toString(), Date.valueOf(LocalDate.now()));
            query.setParameter(EUser.role.toString(), user.getRole());
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
        for (EUser s : EUser.values()) {
            sb.append(s)
                    .append((++count < EUser.values().length) ? "," : ")");
        }
        return sb.toString();
    }

    @Override
    public String getParams() {
        StringBuilder sb = new StringBuilder("(");
        int count = 0;
        for (EUser s : EUser.values()) {
            sb.append(":").append(s)
                    .append((++count < EUser.values().length) ? "," : ")");
        }
        return sb.toString();
    }
}
