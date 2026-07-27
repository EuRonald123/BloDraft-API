package com.blodraft.blog_api.service;

import com.blodraft.blog_api.dto.request.LoginRequest;
import com.blodraft.blog_api.dto.request.RegisterRequest;
import com.blodraft.blog_api.dto.response.JwtResponse;
import com.blodraft.blog_api.exception.BadRequestException;
import com.blodraft.blog_api.model.User;
import com.blodraft.blog_api.model.enums.Role;
import com.blodraft.blog_api.repository.UserRepository;
import com.blodraft.blog_api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse register(RegisterRequest request){
        if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new BadRequestException("Passwords do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already in use");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
