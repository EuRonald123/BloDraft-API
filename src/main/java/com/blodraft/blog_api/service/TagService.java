package com.blodraft.blog_api.service;

import java.util.List;

import com.blodraft.blog_api.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public void deleteById(Long id) {
        Tag tag = findById(id);
        tagRepository.delete(tag);
    }

}