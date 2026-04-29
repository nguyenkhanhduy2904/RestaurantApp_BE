package com.nguyenkhanhduy.restaurant_app.Order;

import java.math.BigDecimal;

public class OrderDetailRequest {
    private Integer productId;
    private String note;
    private Integer quantity;

    public OrderDetailRequest() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
