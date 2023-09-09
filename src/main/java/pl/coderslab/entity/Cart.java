package pl.coderslab.entity;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private final List<CartItem> cartItems;

    public Cart (List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    public void addToCart(CartItem cartItem) {
        cartItems.add(cartItem);
    }
    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }
    public int getTotalItemsQuantity() {
        return  cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }
    public Double getTotalItemsPrice() {
        double value = cartItems.stream().mapToDouble(Cart::getSameProductsValue).sum();

        return Double.valueOf(value);
    }
    private static double getSameProductsValue(CartItem cartItem) {
        return cartItem.getQuantity() * cartItem.getProductId();
    }
    public void removeFromCart(CartItem cartItem) {
        cartItems.remove(cartItem);
    }
}
