package com.mycompany.service.impl;

import com.mycompany.dto.JwtAuthenticationResponse;
import com.mycompany.dto.RefreshTokeRequest;
import com.mycompany.dto.SigninRequest;
import com.mycompany.dto.SignupRequest;
import com.mycompany.entities.Role;
import com.mycompany.entities.User;
import com.mycompany.repository.UserRepository;
import com.mycompany.service.AuthenticationService;
import com.mycompany.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    @Override
    public User signup(SignupRequest request) {
        User user = new User();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.USER);
        user.setPassword( passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return user;
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email or password invalid"));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        return response;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokeRequest request) {
        String email = jwtService.extractUserName(request.getToken());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (jwtService.isTokenValid(request.getToken(), user)) {
            String token = jwtService.generateToken(user);

            JwtAuthenticationResponse response = new JwtAuthenticationResponse();
            response.setToken(token);
            response.setRefreshToken(request.getToken());
            return response;
        }
        return null;
    }
}
