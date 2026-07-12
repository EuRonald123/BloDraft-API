package com.blodraft.blog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 30)
    private String name;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9-]+$")
    private String slug;
}
