package com.blodraft.blog_api.repository;

import com.blodraft.blog_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
