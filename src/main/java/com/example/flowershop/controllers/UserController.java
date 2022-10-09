package com.example.flowershop.controllers;

import com.example.flowershop.models.configmodels.CustomUserDetails;
import com.example.flowershop.models.order.ShowOderDetail;
import com.example.flowershop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/saveOrder")
    public String saveOrder(@AuthenticationPrincipal CustomUserDetails acc, Model model, HttpServletRequest request) {
        if (userService.saveOrder(acc, request)) {
            model.addAttribute("saveOrderStatus","<h1 style=\"color: green\">Cảm ơn quý khách đã mua hàng</h1>");
        }
        else
            model.addAttribute("saveOrderStatus", "<h1 style=\"color: red\">Đơn hàng của quý khách xử lý thất bại</h1>");
        return "forward:/";
    }

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal CustomUserDetails acc, Model model) {
        model.addAttribute("listOrders", userService.userOrders(acc.getAccount().getId()));
        return "/user/profile";
    }

    @GetMapping("/profile/order/{orderId}/details")
    public String showOrderDetails(@PathVariable("orderId") Long orderId, Model model) {
        List<ShowOderDetail> showOderDetailList = userService.allShowOrderDetails(orderId);
        float totalPrice = userService.getPriceOfAllOrderDetails(showOderDetailList);
        model.addAttribute("listOrderDetails", showOderDetailList);
        model.addAttribute("totalPrice", totalPrice);
        return "/user/details";
    }
}
