package com.blodraft.blog_api.controller;

import com.blodraft.blog_api.dto.request.LoginRequest;
import com.blodraft.blog_api.dto.request.RegisterRequest;
import com.blodraft.blog_api.dto.response.JwtResponse;
import com.blodraft.blog_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid LoginRequest request){
        return authService.login(request);
    }
}
