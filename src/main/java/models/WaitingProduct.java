package models;

import intarfaces.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class WaitingProduct implements Entity {
    private final Product product;
    private Integer quantity;

    public WaitingProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "WaitingProduct{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
