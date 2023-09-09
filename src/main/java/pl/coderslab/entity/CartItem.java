package pl.coderslab.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "cart")
@Setter
@Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private int userId;
    @NotNull
    private int productId;
    @NotNull
    private int quantity;

    public CartItem(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItem() {

    }
}
