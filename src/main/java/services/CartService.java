package services;

import lombok.Getter;
import models.Cart;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartService {
    private static final List<Cart> carts = new ArrayList<>();

    private CartService() {
    }

    public static void addCart(Cart cart) {
        carts.add(cart);
    }
}
