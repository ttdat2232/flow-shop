package com.example.flowershop.repositories;

import com.example.flowershop.models.order.OrderDetail;
import com.example.flowershop.models.order.ShowOderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findOrderDetailByOrder_Id(Long orderId);


    @Query("SELECT new com.example.flowershop.models.order.ShowOderDetail(o.id, o.price, o.quantity, p.description, p.imgPath) " +
            "FROM OrderDetail o JOIN Product p " +
            "ON o.product.id = p.id " +
            "WHERE o.order.id = :orderId")
    List<ShowOderDetail> findShowOrderDetails(@Param("orderId")Long orderId);

    @Query("SELECT SUM(od.price) FROM OrderDetail od GROUP BY od.order.id HAVING od.order.id = :orderId")
    Float getTotalPriceByOrderId(@Param("orderId")Long orderId);
}
