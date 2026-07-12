package com.blodraft.blog_api.controller;

import com.blodraft.blog_api.dto.request.CategoryRequest;
import com.blodraft.blog_api.dto.response.CategoryResponse;
import com.blodraft.blog_api.model.Category;
import com.blodraft.blog_api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> findAll(){
        return categoryService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Long id){
        return toResponse(categoryService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody @Valid CategoryRequest request){
        Category category = toEntity(request);
        return toResponse(categoryService.save(category));
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody @Valid CategoryRequest request){
        Category category = toEntity(request);
        category.setId(id);
        return toResponse(categoryService.save(category));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        categoryService.deleteById(id);
    }

    private CategoryResponse toResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .build();
    }

    private Category toEntity(CategoryRequest request){
        return Category.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .build();

    }



}
