package com.blodraft.blog_api.service;

import java.util.List;

import com.blodraft.blog_api.model.Tag;
import com.blodraft.blog_api.repository.TagRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById(Long id){
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found: " + id));
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

}