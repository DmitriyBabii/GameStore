package services;

import models.Cart;
import models.Product;
import models.figures.AuthorizedUser;

public class CartService {
    private static Cart cart;

    private CartService() {
    }

    public static void init(AuthorizedUser user) {
        cart = new Cart(user);
    }

    public static void addProduct(Product product) {
        if (cart != null) {
            cart.addProduct(product);
        }
    }

    public static void exit() {
        cart = null;
    }

    public static void clear() {
        if (cart != null) {
            cart.clear();
        }
    }

    public static Cart getCart() {
        return cart;
    }

    public static boolean isThere(Product product) {
        if (cart == null) {
            return false;
        }
        for (Product p : cart.getProductList()) {
            if (p.equals(product)) {
                return true;
            }
        }
        return false;
    }

    public static int getSummaryPrice() {
        int sum = 0;
        for (Product p : cart.getProductList()) {
            sum += p.getPrice();
        }
        return sum;
    }
}
