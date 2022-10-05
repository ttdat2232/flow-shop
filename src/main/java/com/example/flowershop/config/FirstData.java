package com.example.flowershop.config;


import com.example.flowershop.models.account.Account;
import com.example.flowershop.models.products.Product;
import com.example.flowershop.repositories.AccountRepository;
import com.example.flowershop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class FirstData {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductRepository productRepository;

    @Bean
    public void initiateFirstParam() {
        if (accountRepository.findAccountByUsername("admin") == null && accountRepository.findAccountByUsername("user") == null) {
            Account account = new Account();
            account.setRole(1);
            //password: 12345
            account.setPassword("$2a$10$SMyaYwkvyAIX9fHFywD7beG6yiIarJ3PoR9yg/UWB6TOMCZiel5ci");
            account.setUsername("admin");
            account.setName("Administrator");
            account.setAddress("Q9");
            account.setStatus(1);
            accountRepository.save(account);

            Account account2 = new Account();
            account2.setRole(2);
            account2.setPassword("$2a$10$Az4E.v3uneDHol4zJsNlee3Diu8bTXQ4q1qCf0eNFdSc2H1g.WGLG");
            account2.setUsername("user");
            account2.setName("Tiến Đạt");
            account2.setAddress("Q9");
            account2.setStatus(1);
            accountRepository.save(account2);
        }
        if (!productRepository.findById(1L).isPresent() &&
            !productRepository.findById(2L).isPresent() &&
            !productRepository.findById(3L).isPresent() &&
            !productRepository.findById(4L).isPresent() &&
            !productRepository.findById(5L).isPresent()) {

            Product product = new Product("Hoa Hồng", 10000, "Hoa Hồng Vàng", "https://i.ibb.co/wwQ1zr6/hoa-hong-vang.jpg");
            productRepository.save(product);

            Product product2 = new Product("Hoa Ly", 10000, "Hoa Ly Trắng", "https://i.ibb.co/R0wjgxX/hoa-ly.jpg");
            productRepository.save(product2);

            Product product3 = new Product("Hoa Giấy", 100000, "Hoa Giấy Tím", "https://i.ibb.co/6sbvc7N/HoaGiay.jpg");
            productRepository.save(product3);

            Product product4 = new Product("Hoa Lan", 2000000000, "Lan đột biến", "https://i.ibb.co/qB6FdtM/lan-dot-bien.jpg");
            productRepository.save(product4);

            Product product5 = new Product("Hoa Hồng", 10000, "Hoa Hồng Đỏ", "https://i.ibb.co/3FZR6kf/Rose.jpg");
            productRepository.save(product5);
        }

    }

}
