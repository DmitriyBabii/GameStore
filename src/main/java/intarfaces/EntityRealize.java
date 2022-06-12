package intarfaces;

public interface EntityRealize {
    void createTable();

    void save(Entity... entity);

    void update(Entity... entity);

    void delete(Entity... entity);
}
