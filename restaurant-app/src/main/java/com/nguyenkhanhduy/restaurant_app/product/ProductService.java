package com.nguyenkhanhduy.restaurant_app.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProductList() {
        return productRepository.findAll().stream().map(ProductService::convertToDTO).toList();
    }


    public static ProductDTO convertToDTO(Product product){

        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductDescription(),
                product.getProductThumbnailUrl(),
                product.getCategoryId()
        );
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return ProductService.convertToDTO(product);
    }

    public void deleteProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
       productRepository.delete(product);

    }

    public ProductDTO postProduct(ProductDTO product) {
        Product p = new Product();
        p.setProductName(product.getProductName());
        p.setProductPrice(product.getProductPrice());
        p.setProductDescription(product.getProductDescription());
        p.setProductThumbnailUrl(product.getProductThumbnailUrl());
        p.setCategoryId(product.getProductCategory());

        return convertToDTO(productRepository.save(p));
    }
}
