package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.LoginRequest;
import com.ecommerce.auth_service.dto.RegisterRequest;
import com.ecommerce.auth_service.dto.ValidateTokenResponse;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.repository.UserRepository;
import com.ecommerce.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository repository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(
            RegisterRequest request) {

        if(repository.existsByUsername(
                request.getUsername())) {

            throw new RuntimeException(
                    "Username already exists");
        }

        if(repository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists");
        }

        User user =
                User.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()))
                        .role("CUSTOMER")
                        .build();

        repository.save(user);
    }

    @Override
    public String login(LoginRequest request) {

        User user = repository.findByUsername(
                        request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        boolean validPassword =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!validPassword) {
            throw new RuntimeException(
                    "Invalid credentials");
        }

        return jwtUtil.generateToken(
                user.getUsername());
    }


    @Override
    public ValidateTokenResponse validateToken(
            String token) {

        try {

            String username =
                    jwtUtil.extractUsername(token);

            return new ValidateTokenResponse(
                    true,
                    username
            );

        } catch (Exception e) {

            return new ValidateTokenResponse(
                    false,
                    null
            );
        }
    }
}
