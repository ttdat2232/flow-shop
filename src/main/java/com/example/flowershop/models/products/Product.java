package com.example.flowershop.models.products;

import com.example.flowershop.models.order.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Products")
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String type;

    private float basePrice;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Product(String type, float basePrice, String description) {
        this.type = type;
        this.basePrice = basePrice;
        this.description = description;
    }
}
