package models;

import lombok.Getter;
import models.figures.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart {
    private final User user;
    private final List<Product> productList = new ArrayList<>();

    public Cart(User user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }
}
