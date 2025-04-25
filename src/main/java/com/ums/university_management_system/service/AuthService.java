package com.ums.university_management_system.service;

import com.ums.university_management_system.dto.AuthRequest;
import com.ums.university_management_system.dto.AuthResponse;
import com.ums.university_management_system.dto.RegisterRequest;
import com.ums.university_management_system.model.User;
import com.ums.university_management_system.repository.UserRepository;
import com.ums.university_management_system.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())  // This will now match the RegisterRequest field
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found")); // You can handle this exception as needed
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }
}
