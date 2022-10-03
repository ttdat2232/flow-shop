package com.example.flowershop.repositories;

import com.example.flowershop.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderByDateAndAccount_Id(LocalDate date, Long id);


    List<Order> findByAccount_Id(Long id);

}
