package com.blodraft.blog_api.controller;

import com.blodraft.blog_api.dto.request.PostRequest;
import com.blodraft.blog_api.dto.response.CategoryResponse;
import com.blodraft.blog_api.dto.response.PagedResponse;
import com.blodraft.blog_api.dto.response.PostResponse;
import com.blodraft.blog_api.dto.response.TagResponse;
import com.blodraft.blog_api.model.Category;
import com.blodraft.blog_api.model.Post;
import com.blodraft.blog_api.model.Tag;
import com.blodraft.blog_api.model.enums.PostStatus;
import com.blodraft.blog_api.service.CategoryService;
import com.blodraft.blog_api.service.PostService;
import com.blodraft.blog_api.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;
    private final TagService tagService;

    @GetMapping
    public PagedResponse<PostResponse> findAll(@PageableDefault (size = 10) Pageable pageable,
                                               @RequestParam(required = false) String search){
        Page<Post> page = postService.findAll(search, pageable);
        List<PostResponse> posts = page.getContent().stream()
                .map(this::toResponse)
                .toList();
        return PagedResponse.<PostResponse>builder()
                .content(posts)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @GetMapping("/{id}")
    public PostResponse findById(@PathVariable Long id){
        return toResponse(postService.findById(id));
    }

    @GetMapping("/slug/{slug}")
    public PostResponse findBySlug(@PathVariable String slug){
        return toResponse(postService.findBySlug(slug));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@RequestBody @Valid PostRequest request){
        Post post = toEntity(request);
        return toResponse(postService.save(post));
    }

    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id, @RequestBody @Valid PostRequest request){
        Post existing = postService.findById(id);

        Category category = categoryService.findById(request.getCategoryId());
        Set<Tag> tags = request.getTagIds().stream()
                .map(tagService::findById)
                .collect(Collectors.toSet());

        existing.setTitle(request.getTitle());
        existing.setSlug(postService.generateUniqueSlug(generateSlug(request.getTitle()), id));
        existing.setContent(request.getContent());
        existing.setExcerpt(request.getExcerpt());
        existing.setAuthorName(request.getAuthorName());
        existing.setCategory(category);
        existing.setTags(tags);

        return toResponse(postService.save(existing));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        postService.deleteById(id);
    }

    private PostResponse toResponse(Post post){
        CategoryResponse catResponse = null;
        if(post.getCategory() != null) {
            catResponse = CategoryResponse.builder()
                    .id(post.getCategory().getId())
                    .name(post.getCategory().getName())
                    .slug(post.getCategory().getSlug())
                    .build();
        }

        Set<TagResponse> tagResponses = post.getTags()
                .stream()
                .map(tag -> TagResponse.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .slug(tag.getSlug())
                        .build())
                .collect(Collectors.toSet());

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .slug(post.getSlug())
                .content(post.getContent())
                .excerpt(post.getExcerpt())
                .authorName(post.getAuthorName())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .category(catResponse)
                .tags(tagResponses)
                .build();
    }

    private Post toEntity(PostRequest request){
        Category category = categoryService.findById(request.getCategoryId());

        Set<Tag> tags = request.getTagIds()
                .stream()
                .map(tagService::findById)
                .collect(Collectors.toSet());

        return Post.builder()
                .title(request.getTitle())
                .slug(postService.generateUniqueSlug(generateSlug(request.getTitle())))
                .content(request.getContent())
                .excerpt(request.getExcerpt())
                .authorName(request.getAuthorName())
                .status(PostStatus.PUBLISHED)
                .publishedAt(LocalDateTime.now())
                .category(category)
                .tags(tags)
                .build();
    }

    public static String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("^-+|-+$", "");
    }
}
