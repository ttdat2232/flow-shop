package com.example.flowershop.controllers;

import com.example.flowershop.models.account.Account;
import com.example.flowershop.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private MainService mainService;


    @GetMapping(path = {"/", "index"})
    public String index(Model model) {
        model.addAttribute("productList", mainService.getAllProducts());
        return "index";
    }

    @GetMapping(path = "/addToCart")
    public String addToCart(@RequestParam("productId") long productId, HttpServletRequest request, Model model) {
        if (mainService.addToCart(productId, request))
            model.addAttribute("AddToCartNotice", "<h1 style=\"color: green\">Thêm vào giỏ hàng thành công</h1>\n");
        else
            model.addAttribute("AddToCartNotice", "<h1 style=\"color: red\">Thêm vào giỏ hàng không thành công</h1>\n");
        return "forward:/";
    }

    @GetMapping("/viewCart")
    public String viewCart() {
        return "YourCart";
    }

    @GetMapping("/removeCartItem/{itemKey}")
    public String removeCartItem(@PathVariable("itemKey")Long key, Model model, HttpServletRequest request) {
        if (mainService.removeCartItem(key, request))
            model.addAttribute("removeStatus", "<h2 style=\"color: red\">Xóa thành công</h2>");
        else
            model.addAttribute("removeStatus", "<h2 style=\"color: red\">Xóa không thành công</h2>");
        return "forward:/viewCart";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        Account account = new Account();
        //Default account with manual sign up is user
        account.setRole(2);
        //Default account with manual sign up is active
        account.setStatus(1);
        model.addAttribute("registerAccount", account);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration (@ModelAttribute("registerAccount") Account account, Model model) {
        if (mainService.registration(account)) {
            model.addAttribute("registrationStatus", "<h1 style=\"color: green\">Đăng ký thành công</h1>");
        } else
            model.addAttribute("registrationStatus", "<h1 style=\"color: red\">Đăng ký không thành công</h1>");
        return "registration";
    }

    @GetMapping("/login")
    public String longForm() {
        return "login";
    }
}
