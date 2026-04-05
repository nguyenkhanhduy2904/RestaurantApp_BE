package com.nguyenkhanhduy.restaurant_app.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public static CategoryDTO convertToCategoryDTO(Category category){
        return new CategoryDTO(
                category.getCategoryId(),
                category.getCategoryName());
    }

    public List<CategoryDTO> getCategoryList() {
        List<CategoryDTO> rs = categoryRepository.findAll().stream().map(CategoryService::convertToCategoryDTO).toList();
        return rs;

    }

    public CategoryDTO getCategoryById(Integer id) {
        Category rs  = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToCategoryDTO(rs);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        //need check for dup name? maybe?
        Category c = new Category(categoryDTO.getCategoryName());

        categoryRepository.save(c);
        return convertToCategoryDTO(c);
    }
}
