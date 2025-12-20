package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.CustomerProfile;
import com.example.demo.repository.CustomerProfileRepository;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {
    
    private final CustomerProfileService customerProfileService;
    private final CustomerProfileRepository customerProfileRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CustomerProfileService customerProfileService, 
                         CustomerProfileRepository customerProfileRepository,
                         JwtUtil jwtUtil, 
                         PasswordEncoder passwordEncoder) {
        this.customerProfileService = customerProfileService;
        this.customerProfileRepository = customerProfileRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new customer")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            CustomerProfile customer = new CustomerProfile();
            customer.setCustomerId(request.getEmail());
            customer.setFullName(request.getFullName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
            customer.setRole(request.getRole() != null ? request.getRole() : "ROLE_CUSTOMER");
            customer.setCreatedAt(LocalDateTime.now());
            
            CustomerProfile created = customerProfileService.createCustomer(customer);
            return ResponseEntity.ok(new ApiResponse(true, "Customer registered successfully", created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login customer")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            CustomerProfile customer;
            try {
                customer = customerProfileService.findByCustomerId(request.getEmail());
            } catch (NoSuchElementException e) {
                // Try finding by email if customerId lookup fails
                customer = customerProfileRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("Customer not found"));
            }
            
            if (passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
                String token = jwtUtil.generateToken(customer.getId(), customer.getEmail(), customer.getRole());
                
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("customer", customer);
                
                return ResponseEntity.ok(new ApiResponse(true, "Login successful", response));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid credentials"));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid credentials"));
        }
    }
}