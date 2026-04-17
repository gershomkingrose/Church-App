package com.church.app.auth;

import com.church.app.security.JwtUtil;
import com.church.app.user.User;
import com.church.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthDtos.AuthResponse(token, user.getId(), user.getTenantId(),
                user.getName(), user.getEmail(), user.getRole().name());
    }

    public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request) {
        if (userRepository.existsByEmailAndTenantId(request.getEmail(), request.getTenantId())) {
            throw new RuntimeException("Email already registered for this tenant");
        }

        User user = new User();
        user.setTenantId(request.getTenantId());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.valueOf(request.getRole().toUpperCase()));

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        return new AuthDtos.AuthResponse(token, user.getId(), user.getTenantId(),
                user.getName(), user.getEmail(), user.getRole().name());
    }
}
