package services.entity;

import intarfaces.Entity;
import intarfaces.EntityRealize;
import models.Criterion;

import java.util.List;

public abstract class EntityService implements EntityRealize {

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getColumns();

    protected abstract String getParams();

    protected abstract String getSelectQuery(List<Criterion> criterionList);

    protected abstract List<? extends Entity> getEntities(List<Object[]> resultList);
}
