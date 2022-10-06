package com.example.flowershop.controllers;


import com.example.flowershop.models.order.Order;
import com.example.flowershop.models.order.ShowOderDetail;
import com.example.flowershop.models.products.Product;
import com.example.flowershop.services.AdminService;
import com.example.flowershop.services.MainService;
import com.example.flowershop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MainService mainService;

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

    @GetMapping("/product")
    public String showProductMngPage(Model model) {
        model.addAttribute("productList", mainService.getAllProduct());
        return "admin/productPage";
    }

    @GetMapping("/product/{productId}/delete")
    @Transactional(rollbackFor = Exception.class)
    public String deleteProduct(@PathVariable("productId") long productId, Model model) {
        if (adminService.deleteProduct(productId)){
            model.addAttribute("deleteStatus", "<h2 style='color: red'>Xóa sản phẩm thành công</h2>");
            //Reset the data in view after delete
            mainService.resetAllProducts();
        }
        else
            model.addAttribute("deleteStatus", "<h2 style='color: red'>Xóa sản phẩm không thành công</h2>");
        return "forward:/admin/product";
    }

    @GetMapping("/product/{productId}/update")
    public String showUpdateProductForm(@PathVariable("productId")Long productId, Product product, Model model) {
        product.setId(productId);
        model.addAttribute("updateProduct", product);
        return "admin/update";
    }

    @PostMapping("/product/update")
    public String updateProduct(@ModelAttribute("updateProduct")Product product, Model model) {
        if (adminService.updateProduct(product)) {
            model.addAttribute("updateStatus", "Cập nhật thành công");
            mainService.resetAllProducts();
        } else
            model.addAttribute("updateStatus", "Cập nhật thành công");
        return "admin/update";
    }

    @GetMapping("/product/add")
    public String showAddForm(Product product, Model model) {
        model.addAttribute("product", product);
        return "admin/add";
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product")Product product, Model model) {
        if (adminService.addProduct(product)) {
            model.addAttribute("addStatus", "Thêm vào thành công");
            mainService.resetAllProducts();
        } else
            model.addAttribute("addStatus", "Thêm vào không thành công");
        return "admin/add";
    }
}
