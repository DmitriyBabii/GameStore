package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.enums.EUser;
import models.enums.Role;
import models.figures.*;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public void save(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            AuthorizedUser user = (AuthorizedUser) value;
            ServiceHibernate.getSession().createSQLQuery(getInsertQuery())
                    .setParameter(EUser.id_user.toString(), user.getId())
                    .setParameter(EUser.name.toString(), user.getName())
                    .setParameter(EUser.last_name.toString(), user.getLastName())
                    .setParameter(EUser.username.toString(), user.getUsername())
                    .setParameter(EUser.password.toString(), user.getPassword())
                    .setParameter(EUser.phone_number.toString(), user.getPhoneNumber())
                    .setParameter(EUser.email.toString(), user.getEmail())
                    .setParameter(EUser.date_of_birth.toString(), Date.valueOf(LocalDate.now()))
                    .setParameter(EUser.role.toString(), user.getRole())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void update(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            AuthorizedUser user = (AuthorizedUser) value;
            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(EUser.id_user.toString(), user.getId())
                    .setParameter(EUser.name.toString(), user.getName())
                    .setParameter(EUser.last_name.toString(), user.getLastName())
                    .setParameter(EUser.username.toString(), user.getUsername())
                    .setParameter(EUser.password.toString(), user.getPassword())
                    .setParameter(EUser.phone_number.toString(), user.getPhoneNumber())
                    .setParameter(EUser.email.toString(), user.getEmail())
                    .setParameter(EUser.date_of_birth.toString(), Date.valueOf(LocalDate.now()))
                    .setParameter(EUser.role.toString(), user.getRole())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            AuthorizedUser user = (AuthorizedUser) value;
            ServiceHibernate.getSession()
                    .createSQLQuery(getDeleteQuery())
                    .setParameter(EUser.id_user.toString(), user.getId())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public List<AuthorizedUser> select(List<Criterion> criterionList) {
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
                .append(columns[EUser.id_user.ordinal()])
                .append("=")
                .append(params[EUser.id_user.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM gameshop.user ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[EUser.id_user.ordinal()])
                .append("=")
                .append(params[EUser.id_user.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM gameshop.user WHERE ");
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
    protected List<AuthorizedUser> getEntities(List<Object[]> resultList) {
        List<AuthorizedUser> productList = new ArrayList<>();
        AuthorizedUser user = null;
        for (Object[] o : resultList) {
            switch (Role.valueOf((String) o[8])) {
                case CLIENT: {
                    user = new Client(
                            (String) o[EUser.id_user.ordinal()],
                            (String) o[EUser.name.ordinal()],
                            (String) o[EUser.last_name.ordinal()],
                            (String) o[EUser.username.ordinal()],
                            (String) o[EUser.password.ordinal()],
                            (String) o[EUser.phone_number.ordinal()],
                            (String) o[EUser.email.ordinal()],
                            (Date) o[EUser.date_of_birth.ordinal()]
                    );
                    break;
                }
                case MANAGER: {
                    user = new Manager(
                            (String) o[EUser.id_user.ordinal()],
                            (String) o[EUser.name.ordinal()],
                            (String) o[EUser.last_name.ordinal()],
                            (String) o[EUser.username.ordinal()],
                            (String) o[EUser.password.ordinal()],
                            (String) o[EUser.phone_number.ordinal()],
                            (String) o[EUser.email.ordinal()],
                            (Date) o[EUser.date_of_birth.ordinal()]
                    );
                    break;
                }
                case STOREKEEPER: {
                    user = new Storekeeper(
                            (String) o[EUser.id_user.ordinal()],
                            (String) o[EUser.name.ordinal()],
                            (String) o[EUser.last_name.ordinal()],
                            (String) o[EUser.username.ordinal()],
                            (String) o[EUser.password.ordinal()],
                            (String) o[EUser.phone_number.ordinal()],
                            (String) o[EUser.email.ordinal()],
                            (Date) o[EUser.date_of_birth.ordinal()]
                    );
                    break;
                }
                case COURIER: {
                    user = new Courier(
                            (String) o[EUser.id_user.ordinal()],
                            (String) o[EUser.name.ordinal()],
                            (String) o[EUser.last_name.ordinal()],
                            (String) o[EUser.username.ordinal()],
                            (String) o[EUser.password.ordinal()],
                            (String) o[EUser.phone_number.ordinal()],
                            (String) o[EUser.email.ordinal()],
                            (Date) o[EUser.date_of_birth.ordinal()]
                    );
                    break;
                }
            }
            if (user != null) {
                productList.add(user);
            }
        }
        return productList;
    }
}
