package com.blodraft.blog_api.service;

import com.blodraft.blog_api.exception.ResourceNotFoundException;
import com.blodraft.blog_api.model.Post;
import com.blodraft.blog_api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> findAll(String search, Pageable pageable) {
        if (search != null) {
            return postRepository.search(search, pageable);
        }
            return postRepository.findAll(pageable);
    }

    public Post findById(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
    }

    public Post save(Post post){
        return postRepository.save(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public Post findBySlug(String slug){
        return postRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found " + slug));
    }

}


