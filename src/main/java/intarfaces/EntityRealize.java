package intarfaces;

import models.Criterion;

import java.util.List;

public interface EntityRealize {
    void createTable();

    void save(Entity... entity);

    void update(Entity... entity);

    void delete(Entity... entity);

    List<? extends Entity> select(List<Criterion> criterionList);
}
