package models;

import intarfaces.EntityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import models.enums.Operator;

@Getter
@AllArgsConstructor
public class Criterion {
    private final EntityEnum parameter;
    private final Operator operator;
    private final Object value;

    public Criterion(EntityEnum parameter, Object value) {
        this.parameter = parameter;
        this.operator = Operator.EQUALS;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Criterion{" +
                "parameter=" + parameter +
                ", operator=" + operator +
                ", value=" + value +
                '}';
    }
}
