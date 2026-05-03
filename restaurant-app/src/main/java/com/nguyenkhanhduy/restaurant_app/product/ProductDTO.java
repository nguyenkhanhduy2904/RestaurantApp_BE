package com.nguyenkhanhduy.restaurant_app.product;

import java.math.BigDecimal;

public class ProductDTO {

    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private String productThumbnailUrl;
    private Integer categoryId;
    private String status;
    private Integer priceReduction;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProductDTO() {

    }

    public ProductDTO(Integer productId, String productName, BigDecimal productPrice, String productDescription, String productThumbnailUrl, Integer categoryId, String status, Integer priceReduction) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productThumbnailUrl = productThumbnailUrl;
        this.categoryId = categoryId;
        this.status = status;
        this.priceReduction = priceReduction;
    }

    //    public ProductDTO(Integer productId, String productName, BigDecimal productPrice, String productDescription, String productThumnailUrl, Integer categoryId) {
//        this.productId = productId;
//        this.productName = productName;
//        this.productPrice = productPrice;
//        this.productDescription = productDescription;
//        this.productThumbnailUrl = productThumnailUrl;
//        this.categoryId = categoryId;
//    }

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPriceReduction() {
        return priceReduction;
    }

    public void setPriceReduction(Integer priceReduction) {
        this.priceReduction = priceReduction;
    }
}
