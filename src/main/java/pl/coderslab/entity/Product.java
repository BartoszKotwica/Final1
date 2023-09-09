package pl.coderslab.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private double price;
    @NotNull
    private String description;
    @NotNull
    private int quantity;


    public Product(String name, Double price, String description, int quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public Product() {

    }
}
