package models;

import intarfaces.EntityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Criterion {
    private final EntityEnum parameter;
    private final Object value;
}
