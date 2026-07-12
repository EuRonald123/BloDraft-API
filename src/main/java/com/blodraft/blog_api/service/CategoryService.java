package com.blodraft.blog_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.blodraft.blog_api.exception.ResourceNotFoundException;
import com.blodraft.blog_api.repository.CategoryRepository;
import com.blodraft.blog_api.model.Category;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

}