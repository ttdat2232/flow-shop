package com.example.flowershop.repositories;

import com.example.flowershop.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query("SELECT o FROM Order o WHERE o.date = :date AND o.account.id = :id ")
    Order findOrderByDateAndAccount_Id(LocalDate date, Long id);


    @Query("SELECT o FROM Order  o WHERE o.account.id = :id")
    List<Order> findByAccount_Id(@Param("id")Long id);

}
