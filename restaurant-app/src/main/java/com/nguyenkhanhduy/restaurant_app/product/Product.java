package com.nguyenkhanhduy.restaurant_app.product;


import com.nguyenkhanhduy.restaurant_app.category.Category;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name ="thumbnail_url")
    private String productThumbnailUrl;
    @Column(name ="product_status")
    private String status;
    @Column(name ="product_price_reduction")
    private Integer priceReduction;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    //    @Column(name = "category_id")
//    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id") // FK column in DB
    private Category category;



    public Product() {
    }

    public Product(String productName, BigDecimal productPrice, String productDescription, String productThumbnailUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productThumbnailUrl = productThumbnailUrl;
    }

    public Product(Integer productId, String productName, BigDecimal productPrice, String productDescription, String productThumbnailUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productThumbnailUrl = productThumbnailUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductThumbnailUrl() {
        return productThumbnailUrl;
    }

    public void setProductThumbnailUrl(String productThumbnailUrl) {
        this.productThumbnailUrl = productThumbnailUrl;
    }

//    public Integer getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }


    public Integer getPriceReduction() {
        return priceReduction;
    }

    public void setPriceReduction(Integer priceReduction) {
        this.priceReduction = priceReduction;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
