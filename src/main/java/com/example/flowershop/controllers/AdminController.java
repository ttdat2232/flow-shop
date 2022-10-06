package com.example.flowershop.controllers;


import com.example.flowershop.models.order.Order;
import com.example.flowershop.models.order.ShowOderDetail;
import com.example.flowershop.services.AdminService;
import com.example.flowershop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("accountList", adminService.getAccountList());
        return "admin/index";
    }

    @GetMapping("/block/{accountId}")
    public String blockUser(@PathVariable("accountId") Long id, Model model) {
        if(adminService.blockUser(id))
            model.addAttribute("accountStatus", "<h2 style='color: red'>Đã chặn người dùng này</h2>");
        else
            model.addAttribute("accountStatus", "<h2 style='color: red'>Chặn người dùng thất bại</h2>");
        return "forward:/admin";
    }

    @GetMapping("/unblock/{accountId}")
    public String unblockUser(@PathVariable("accountId") Long id, Model model) {
        if (adminService.unBlockUser(id))
            model.addAttribute("accountStatus", "<h2 style='color: green'>Đã bỏ chặn người dùng này</h2>");
        else
            model.addAttribute("accountStatus", "<h2 style='color: green'>Bỏ chặn người dùng này thất bại</h2>");
        return "forward:/admin";
    }

    @GetMapping("/account/{accountId}/{name}/order")
    public String showUserOder(@PathVariable("accountId")Long accountId, @PathVariable("name")String name, Model model) {
        model.addAttribute("userName", name);
        model.addAttribute("ordersList", userService.userOrders(accountId));
        return "admin/userOrder";
    }

    @GetMapping("/order/{orderId}/details")
    public String showOrderDetail(@PathVariable("orderId")Long orderId, Model model) {
        List<ShowOderDetail> showOderDetailList =  userService.allShowOrderDetails(orderId);
        model.addAttribute("totalPrice", userService.getPriceOfAllOrderDetails(showOderDetailList));
        model.addAttribute("orderId", orderId);
        model.addAttribute("listOrderDetails", showOderDetailList);
        return "admin/orderDetail";
    }
}
