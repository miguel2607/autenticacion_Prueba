package org.arle.proyectorolesauth.service;

import org.arle.proyectorolesauth.controller.LoginRequest;
import org.arle.proyectorolesauth.controller.RegisterRequest;
import org.arle.proyectorolesauth.entity.User;
import org.arle.proyectorolesauth.repository.UserRepository;
import org.arle.proyectorolesauth.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            return jwtService.generateToken(user);
        } catch (Exception e) {
            System.err.println("Error durante el registro: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return jwtService.generateToken(user);
    }
}
