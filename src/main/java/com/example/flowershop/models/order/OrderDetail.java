package com.example.flowershop.models.order;

import com.example.flowershop.models.products.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OrderDetails")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private int quantity;
    private float price;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public OrderDetail( int quantity, float price) {
//        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail(int quantity, float price, Order order) {
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }
}
