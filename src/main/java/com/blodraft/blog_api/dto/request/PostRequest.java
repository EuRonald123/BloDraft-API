package com.blodraft.blog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Title must contain only letters and spaces")
    private String title;

    @NotBlank
    @Size(max = 50000)
    private String content;

    @Size(max = 350)
    private String excerpt;

    @NotBlank
    @Size(max = 30)
    private String authorName;

    @NotNull
    private Long categoryId;

    @NotNull
    private Set<Long> tagIds;
}
