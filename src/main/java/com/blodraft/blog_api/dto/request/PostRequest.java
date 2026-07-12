package com.blodraft.blog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String excerpt;
    private String authorName;

    private Long categoryId;

    private Set<Long> tagIds;
}
