package models;

import lombok.Getter;
import models.figures.AuthorizedUser;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart {
    private final AuthorizedUser user;
    private final List<Product> productList = new ArrayList<>();

    public Cart(AuthorizedUser user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void clear(){
        productList.clear();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                ", productList=" + productList +
                '}';
    }
}
