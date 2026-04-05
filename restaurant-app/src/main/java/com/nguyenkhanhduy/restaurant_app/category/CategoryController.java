package com.nguyenkhanhduy.restaurant_app.category;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getCategoryList(){
        List<CategoryDTO> ls = categoryService.getCategoryList();
        return ResponseEntity.status(HttpStatus.OK).body(ls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id){

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO){


        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(id, categoryDTO));


    }
}
