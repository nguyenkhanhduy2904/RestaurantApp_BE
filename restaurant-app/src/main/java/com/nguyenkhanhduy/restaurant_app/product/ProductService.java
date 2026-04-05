package com.nguyenkhanhduy.restaurant_app.product;

import com.nguyenkhanhduy.restaurant_app.category.Category;
import com.nguyenkhanhduy.restaurant_app.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
                product.getCategory().getCategoryId()
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

        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        p.setCategory(category);

        return convertToDTO(productRepository.save(p));
    }

    public ProductDTO updateProduct(int id, ProductDTO product) {

        if(id != product.getProductId()){
            throw new IllegalArgumentException("ID given in the path and ID of this product are not match.");
        }

        Product p = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + product.getProductId()));

        p.setProductName(product.getProductName());
        p.setProductPrice(product.getProductPrice());

        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        p.setCategory(category);
        p.setProductDescription(product.getProductDescription());
        p.setProductThumbnailUrl(product.getProductThumbnailUrl());

        return convertToDTO(productRepository.save(p));
    }
}
