package com.example.flowershop.models.account;



import com.example.flowershop.models.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String username;
    private String password;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    private String address;
    private int status;
    private int role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Order.class)
    private List<Order> orders = new ArrayList<>();

}
