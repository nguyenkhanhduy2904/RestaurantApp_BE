package com.nguyenkhanhduy.restaurant_app.Image;

import jakarta.persistence.*;

import jakarta.persistence.Id;

@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Integer id;

    @Column(name = "product_image_url")
    private String url;

    @Column(name = "product_image_order")
    private Integer order;

    @Column(name = "product_product_id")
    private Integer productId;

    public ProductImage() {
    }

    public ProductImage(String url, Integer order, Integer productId) {
        this.url = url;
        this.order = order;
        this.productId = productId;
    }

    public ProductImage(Integer id, String url, Integer order, Integer productId) {
        this.id = id;
        this.url = url;
        this.order = order;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
