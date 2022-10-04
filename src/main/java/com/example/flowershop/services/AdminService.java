package com.example.flowershop.services;

import com.example.flowershop.models.account.Account;
import com.example.flowershop.models.order.Order;
import com.example.flowershop.models.order.OrderDetail;
import com.example.flowershop.repositories.AccountRepository;
import com.example.flowershop.repositories.OrderDetailRepository;
import com.example.flowershop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private List<Account> accountList = new ArrayList<>();

    private List<OrderDetail> odList = new ArrayList<>();

    private List<Order> orderList = new ArrayList<>();

    public List<Account> getAccountList() {
        if (this.accountList.isEmpty())
            this.accountList = accountRepository.findAll();
        return this.accountList;
    }

    public List<Order> getOrderList() {
        if (this.orderList.isEmpty())
            this.orderList = orderRepository.findAll();
        return this.orderList;
    }

    public List<OrderDetail> getOdList() {
        if (this.odList.isEmpty())
            this.odList = orderDetailRepository.findAll();
        return odList;
    }

}
