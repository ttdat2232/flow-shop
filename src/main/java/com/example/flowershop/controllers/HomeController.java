package com.example.flowershop.controllers;

import com.example.flowershop.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return mainService.addToCart(productId, request, model);
    }

    @GetMapping("/viewCart")
    public String viewCart() {
        return "YourCart";
    }


}
