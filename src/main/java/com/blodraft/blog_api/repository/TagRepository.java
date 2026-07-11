package com.blodraft.blog_api.repository;

import com.blodraft.blog_api.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
