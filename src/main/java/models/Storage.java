package models;

import intarfaces.Entity;
import lombok.*;

@Setter
@Getter
@Builder
public class Storage implements Entity {
    private final Product product;
    private Integer quantity;

    public Storage(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
