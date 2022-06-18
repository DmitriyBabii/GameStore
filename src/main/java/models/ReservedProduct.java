package models;

import intarfaces.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ReservedProduct implements Entity {
    private final Product product;
    private Integer quantity;

    public ReservedProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ReservedProduct{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
