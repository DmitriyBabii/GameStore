package models.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MySQLType {
    ID(" VARCHAR(36)"),
    INT(" INT"),
    DOUBLE(" DOUBLE"),
    DATE(" DATE"),
    SHORT_STRING(" VARCHAR(30)"),
    LONG_STRING(" VARCHAR(255)"),
    PHONE_NUMBER(" VARCHAR(13)");

    private final String query;

    @Override
    public String toString() {
        return query;
    }
}
