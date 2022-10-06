package com.example.flowershop.services;

import com.example.flowershop.models.account.Account;
import com.example.flowershop.models.cart.CartLineInfo;
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

    @Autowired
    private AdminService adminService;

    private List<Product> availableProductList = new ArrayList<>();

    private List<Product> allProduct = new ArrayList<>();

    public void resetAllProducts() {
        this.allProduct = productRepository.findAll();
    }

    public List<Product> getAllProduct() {
        if (this.allProduct.isEmpty())
            this.allProduct = productRepository.findAll();
        return this.allProduct;
    }

    public void resetAllAvailableProducts() {
        this.availableProductList = productRepository.findAllByStatus(1);
    }

    public List<Product> getAllAvailableProducts() {
        if (this.availableProductList.isEmpty())
            this.availableProductList = productRepository.findAllByStatus(1);
        return this.availableProductList;
    }


    public boolean addToCart(long productId, HttpServletRequest request) {
        Optional<Product> productOptional = productRepository.findById(productId);

        Map<Long, CartLineInfo> cart = (Map<Long, CartLineInfo>) request.getSession().getAttribute("cartLine");
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            CartLineInfo item = new CartLineInfo(product.getDescription(), product.getImgPath(), 1, product.getBasePrice() * 1);
            if (cart == null) {
                cart = new HashMap<>();
                cart.put(productId, item);
            } else {
                CartLineInfo tmpItem = cart.get(productId);

                if (tmpItem == null) cart.put(productId, item);
                else {
                    tmpItem.setQuantity(tmpItem.getQuantity() + 1);
                    tmpItem.setPrice(product.getBasePrice() * tmpItem.getQuantity());
                    cart.put(productId, tmpItem);
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
        adminService.getAccountList().add(account);
        return true;
    }

    public boolean removeCartItem(Long key, HttpServletRequest request) {
        Map<Long, CartLineInfo> cart = (Map<Long, CartLineInfo>) request.getSession().getAttribute("cartLine");
        if (cart.get(key) == null) return false;
        cart.remove(key);
        request.getSession().setAttribute("cartLine", cart);
        return true;
    }
}
