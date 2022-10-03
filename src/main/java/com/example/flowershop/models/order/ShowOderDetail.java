// This class is use for view (DTO)

package com.example.flowershop.models.order;

public class ShowOderDetail {
    private Long id;
    private float price;
    private int quantity;
    private String productDescription;

    public ShowOderDetail(Long id, float price, int quantity, String productDescription) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.productDescription = productDescription;
    }

    public ShowOderDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
