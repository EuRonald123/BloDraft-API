package com.blodraft.blog_api.repository;

import com.blodraft.blog_api.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query( "SELECT distinct p FROM Post p " +
            "LEFT join p.tags t " +
            "WHERE cast(p.title as string ) like lower(concat('%', :search, '%')) " +
            "OR cast(p.content as string) like LOWER(CONCAT('%', :search, '%')) " +
            "OR cast(t.name as string) like LOWER(CONCAT('%', :search, '%')) ")
    Page<Post> search(@Param("search") String search, Pageable pageable);


    Optional<Post> findBySlug(String slug);

}
