package com.example.flowershop.services;

import com.example.flowershop.models.account.Account;
import com.example.flowershop.models.products.Product;
import com.example.flowershop.repositories.AccountRepository;
import com.example.flowershop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MainService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public String addToCart(long productId, HttpServletRequest request, Model model) {
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
            model.addAttribute("AddToCartNotice", "<h1 style=\"color: green\">Thêm vào giỏ hàng thành công</h1>\n");
            request.getSession().setAttribute("cartLine", cart);
        } else
            model.addAttribute("AddToCartNotice", "<h1 style=\"color: red\">Thêm vào giỏ hàng không thành công</h1>\n");
        return "forward:/index";
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
