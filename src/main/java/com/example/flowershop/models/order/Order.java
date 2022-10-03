package com.example.flowershop.models.order;

import com.example.flowershop.models.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order(LocalDate date) {
        this.date = date;
    }
}
