package services.entity;

import intarfaces.Entity;
import intarfaces.EntityRealize;
import org.hibernate.query.NativeQuery;

public abstract class EntityService implements EntityRealize {
    protected abstract NativeQuery setAllParams(NativeQuery query, Entity entity);

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getColumns();

    protected abstract String getParams();
}
