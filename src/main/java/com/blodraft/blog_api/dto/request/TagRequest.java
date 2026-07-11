package com.blodraft.blog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Slug is required")
    private String slug;
}
