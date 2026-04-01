package com.nguyenkhanhduy.restaurant_app.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> GetProductList(){
        return productService.getProductList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }


    @PostMapping()
    public ResponseEntity<ProductDTO> postProduct(@RequestBody ProductDTO product){
        ProductDTO productDTO =productService.postProduct(product);
        System.out.println(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Integer id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();

    }

}
