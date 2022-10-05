package com.example.flowershop.controllers;


import com.example.flowershop.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

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

}
