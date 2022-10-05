package com.example.flowershop.models.cart;

import com.example.flowershop.models.products.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartLineInfo {
    private String productDescription;
    private String imgPath;
    private int quantity;
}
