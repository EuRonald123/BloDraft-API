package com.blodraft.blog_api.controller;

import com.blodraft.blog_api.dto.request.TagRequest;
import com.blodraft.blog_api.dto.response.TagResponse;
import com.blodraft.blog_api.model.Tag;
import com.blodraft.blog_api.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.blodraft.blog_api.controller.PostController.*;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagResponse> findAll(){
        return tagService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TagResponse findById(@PathVariable Long id){
        return toResponse(tagService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponse create(@Valid @RequestBody TagRequest request){
        Tag tag = toEntity(request);
        return toResponse(tagService.save(tag));
    }

    @PutMapping("/{id}")
    public TagResponse update(@PathVariable Long id, @RequestBody @Valid TagRequest request){
        Tag tag = toEntity(request);
        tag.setId(id);
        return toResponse(tagService.save(tag));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        tagService.deleteById(id);
    }

    private TagResponse toResponse(Tag tag){
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .build();
    }

    private Tag toEntity(TagRequest request){
        String slug = request.getSlug();
        if (slug == null || slug.isBlank()) {
            slug = generateSlug(request.getName());
        }
        return Tag.builder()
                .name(request.getName())
                .slug(slug)
                .build();
    }
}
