package com.nguyenkhanhduy.restaurant_app.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {
    private String address;
    private String phone;
    private String orderStatus;
    private String paymentStatus;
    private String paymentMethod;

    private Integer userId;

    private List<OrderDetailRequest> orderDetailRequests;


    public OrderRequest() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<OrderDetailRequest> getOrderDetailRequests() {
        return orderDetailRequests;
    }

    public void setOrderDetailRequests(List<OrderDetailRequest> orderDetailRequests) {
        this.orderDetailRequests = orderDetailRequests;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
