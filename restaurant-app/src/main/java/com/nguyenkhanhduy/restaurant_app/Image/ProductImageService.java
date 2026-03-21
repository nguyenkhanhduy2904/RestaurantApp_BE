package com.nguyenkhanhduy.restaurant_app.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }


//    public List<String> getImageURL(Integer productId){
//        List<ProductImage> lsImg =  productImageRepository.findByProductId(productId);
//        List<String> resultUrlList = new ArrayList<>();
//
//        return resultUrlList
//
//    }

}
