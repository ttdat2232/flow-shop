package com.example.flowershop.services;

import com.example.flowershop.models.account.Account;
import com.example.flowershop.models.cart.CartLineInfo;
import com.example.flowershop.models.configmodels.CustomUserDetails;
import com.example.flowershop.models.order.Order;
import com.example.flowershop.models.order.OrderDetail;
import com.example.flowershop.models.order.ShowOderDetail;
import com.example.flowershop.models.products.Product;
import com.example.flowershop.repositories.AccountRepository;
import com.example.flowershop.repositories.OrderDetailRepository;
import com.example.flowershop.repositories.OrderRepository;
import com.example.flowershop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service

public class UserService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    public boolean saveOrder(CustomUserDetails account, HttpServletRequest request) {
        boolean check = false;
        Account acc = account.getAccount();
        Map<Long, CartLineInfo> cartLine = (Map<Long, CartLineInfo>) request.getSession().getAttribute("cartLine");
        LocalDate currentDate = LocalDate.now();
        Order order = orderRepository.findOrderByDateAndAccount_Id(currentDate, acc.getId());
        Order createdOrder;

        if (order == null) {
            order = new Order();
            order.setDate(currentDate);
            order.setAccount(acc);
            acc.getOrders().add(order);
            accountRepository.save(acc);
            createdOrder = orderRepository.findOrderByDateAndAccount_Id(currentDate, acc.getId());
        } else createdOrder = order;

        for (Long productId : cartLine.keySet()) {
            OrderDetail orderDetail = new OrderDetail();
            Product product = productRepository.findById(productId).get();
            // set order detail attribute
            orderDetail.setOrder(createdOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(cartLine.get(productId).getQuantity());
            orderDetail.setPrice(cartLine.get(productId).getQuantity() * product.getBasePrice());
            product.getOrderDetails().add(orderDetail);
            //save order
            productRepository.save(product);
            check = true;
        }

        //remove cartLine when successfully store everything into database
        if (check)
            request.getSession().removeAttribute("cartLine");

        return check;
    }

    public List<Order> userOrders(Long accId) {
        return orderRepository.findByAccount_Id(accId);
    }

    public List<ShowOderDetail> allShowOrderDetails(Long orderId) {
        return orderDetailRepository.findShowOrderDetails(orderId);
    }
}
