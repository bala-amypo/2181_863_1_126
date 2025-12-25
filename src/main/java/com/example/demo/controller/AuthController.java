package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.CustomerProfile;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.CustomerProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {
    
    private final CustomerProfileService customerProfileService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    
    public AuthController(CustomerProfileService customerProfileService, 
                         JwtUtil jwtUtil, 
                         PasswordEncoder passwordEncoder) {
        this.customerProfileService = customerProfileService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Register new customer", description = "Register a new customer account")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            CustomerProfile customer = new CustomerProfile();
            customer.setCustomerId(request.getEmail()); // Using email as customer ID for simplicity
            customer.setFullName(request.getFullName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setCurrentTier("BRONZE");
            customer.setActive(true);
            customer.setCreatedAt(LocalDateTime.now());
            
            CustomerProfile created = customerProfileService.createCustomer(customer);
            return ResponseEntity.ok(new ApiResponse(true, "Customer registered successfully", created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login customer", description = "Authenticate customer and return JWT token")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            CustomerProfile customer = customerProfileService.findByCustomerId(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
            
            String token = jwtUtil.generateToken(customer.getId(), customer.getEmail(), "USER");
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("customer", customer);
            
            return ResponseEntity.ok(new ApiResponse(true, "Login successful", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid credentials"));
        }
    }
}