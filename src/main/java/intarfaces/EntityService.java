package intarfaces;

public interface EntityService {
    void createTable();
    void save(Entity entity);
    void update(Entity entity);
    void delete(Entity entity);
    String getColumns();
    String getParams();
}
