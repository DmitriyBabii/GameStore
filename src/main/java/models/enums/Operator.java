package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Operator {
    EQUALS("="),
    LESS("<"),
    GREATER(">"),
    LESS_EQUALS("<="),
    GREATER_EQUALS(">="),
    NOT_EQUALS("!="),
    IS_NULL(" IS NULL"),
    NOT_NULL(" IS NOT NULL"),
    LIKE(" LIKE");

    private final String query;
}
