package services.entity;

import intarfaces.EntityRealize;

public abstract class EntityService implements EntityRealize {

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getColumns();

    protected abstract String getParams();
}
