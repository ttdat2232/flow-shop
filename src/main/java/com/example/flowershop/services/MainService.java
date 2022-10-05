package com.example.flowershop.services;

import com.example.flowershop.models.account.Account;
import com.example.flowershop.models.products.Product;
import com.example.flowershop.repositories.AccountRepository;
import com.example.flowershop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MainService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    private List<Product> productList = new ArrayList<>();

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getAllProducts() {
        if (this.productList.isEmpty())
            this.productList = productRepository.findAll();
        return this.productList;
    }

//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }

    public boolean addToCart(long productId, HttpServletRequest request) {
        Optional<Product> product = productRepository.findById(productId);
        Map<Long, Integer> cart = (Map<Long, Integer>) request.getSession().getAttribute("cartLine");
        if (product.isPresent()) {
            if (cart == null) {
                cart = new HashMap<>();
                cart.put(productId, 1);
            } else {
                Integer tempItem = cart.get(productId);
                if (tempItem == null) cart.put(productId, 1);
                else {
                    tempItem++;
                    cart.put(productId, tempItem);
                }
            }
            request.getSession().setAttribute("cartLine", cart);
            return true;

        }
        return false;
    }

    public boolean registration(Account account) {
        Account checkAcc = accountRepository.findAccountByUsername(account.getUsername());
        // Username of account has already exited
        if (checkAcc != null) return false;
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        accountRepository.save(account);
        return true;
    }
}
