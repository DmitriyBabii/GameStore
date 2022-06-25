package services.entity;

import intarfaces.Entity;
import intarfaces.EntityEnum;
import intarfaces.EntityRealize;
import models.Criterion;
import models.enums.OrderBy;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

import java.util.List;

public abstract class EntityService implements EntityRealize {

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getColumns();

    protected abstract String getParams();

    public final List<? extends Entity> select(List<Criterion> criterionList) {
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

    public final List<? extends Entity> select(List<Criterion> criterionList, EntityEnum param, OrderBy orderBy) {
        ServiceHibernate.open();
        @SuppressWarnings("rawtypes")
        NativeQuery query = ServiceHibernate.getSession()
                .createSQLQuery(getSelectQuery(criterionList) + " ORDER BY " + param + orderBy.toString());
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

    protected abstract String getSelectQuery(List<Criterion> criterionList);

    protected final String useCriterion(StringBuilder sb, List<Criterion> criterionList) {
        for (int i = 0; i < criterionList.size(); i++) {
            Object o = criterionList.get(i).getValue();
            sb.append(criterionList.get(i).getParameter())
                    .append(criterionList.get(i).getOperator().getQuery())
                    .append((o != null) ? (":" + criterionList.get(i).getParameter()) : "")
                    .append((i + 1) < criterionList.size() ? " AND " : "");
        }
        return sb.toString();
    }

    protected abstract List<? extends Entity> getEntities(List<Object[]> resultList);
}
