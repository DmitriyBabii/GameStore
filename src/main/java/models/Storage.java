package models;

import intarfaces.Entity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class Storage implements Entity {
    private final String id;
    private final Product product;
    private Integer quantity;

    public Storage(Product product, Integer quantity) {
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id='" + id + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
