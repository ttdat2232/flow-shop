package com.example.flowershop.controllers;


import com.example.flowershop.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


}
