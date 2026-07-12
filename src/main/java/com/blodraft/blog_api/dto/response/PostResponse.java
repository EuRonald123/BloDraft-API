package com.blodraft.blog_api.dto.response;

import com.blodraft.blog_api.model.enums.PostStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String excerpt;
    private String authorName;
    private PostStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CategoryResponse category;
    private Set<TagResponse> tags;
}
