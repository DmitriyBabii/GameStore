package models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Storage {
    @Setter(value = AccessLevel.NONE)
    private Product product;
    private int quantity;
}
