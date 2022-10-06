package com.example.flowershop.services;

import com.example.flowershop.models.account.Account;
import com.example.flowershop.models.order.Order;
import com.example.flowershop.models.order.OrderDetail;
import com.example.flowershop.models.products.Product;
import com.example.flowershop.repositories.AccountRepository;
import com.example.flowershop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;


    private List<Account> accountList = new ArrayList<>();

    private List<OrderDetail> odList = new ArrayList<>();

    private List<Order> orderList = new ArrayList<>();


    public List<Account> getAccountList() {
        if (this.accountList.isEmpty())
            this.accountList = accountRepository.findAll();
        return this.accountList;
    }


    public boolean blockUser(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setStatus(0);
            accountRepository.save(account);
            this.accountList = accountRepository.findAll();
            return true;
        }
        return false;
    }

    public boolean unBlockUser(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setStatus(1);
            accountRepository.save(account);
            this.accountList = accountRepository.findAll();
            return true;
        }
        return false;
    }

    //Instead of deleting, I'll change a product's status to 0 to avoid loosing data
    public boolean deleteProduct(long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            optionalProduct.get().setStatus(0);
            productRepository.save(optionalProduct.get());
            return true;
        }
        return false;
    }

    public boolean updateProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isPresent()) {
            if (product.getBasePrice() != 0) optionalProduct.get().setBasePrice(product.getBasePrice());
            if (!product.getDescription().equals("")) optionalProduct.get().setDescription(product.getDescription());
            if (!product.getImgPath().equals("")) optionalProduct.get().setImgPath(product.getImgPath());
            optionalProduct.get().setStatus(product.getStatus());
            productRepository.save(optionalProduct.get());
            return true;
        }
        return false;
    }

    public boolean addProduct(Product product) {
        if (!productRepository.findByDescription(product.getDescription()).isPresent()) {
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
