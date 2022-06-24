package models.enums;

public enum OrderBy {
    ASC(" ASC"),
    DESC(" DESC");

    private final String query;

    OrderBy(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return query;
    }
}
