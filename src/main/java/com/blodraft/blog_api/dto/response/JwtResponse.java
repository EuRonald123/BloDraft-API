package com.blodraft.blog_api.dto.response;

import com.blodraft.blog_api.model.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String type;
    private Long id;
    private String name;
    private String email;
    private Role role;
}
