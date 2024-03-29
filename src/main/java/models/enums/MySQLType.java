package models.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MySQLType {
    ID(" VARCHAR(36)"),
    INT(" INT"),
    DOUBLE(" DOUBLE"),
    DATE(" DATE"),
    SHORT_STRING(" VARCHAR(30)"),
    MIDDLE_STRING(" VARCHAR(100)"),
    LONG_STRING(" VARCHAR(2000)"),
    PHONE_NUMBER(" VARCHAR(13)"),
    TINYINT(" TINYINT");

    private final String query;

    @Override
    public String toString() {
        return query;
    }
}
