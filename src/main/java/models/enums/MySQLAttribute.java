package models.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MySQLAttribute {
    NOT_NULL(" NOT NULL"),
    UNIQUE(" UNIQUE");

    private final String query;

    @Override
    public String toString() {
        return query;
    }
}
