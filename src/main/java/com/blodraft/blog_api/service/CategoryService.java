package com.blodraft.blog_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blodraft.blog_api.repository.CategoryRepository;
//import com.blodraft.blog_api.model.Category;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


}
