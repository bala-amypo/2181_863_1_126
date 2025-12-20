package com.example.demo.controller;

import com.example.demo.entity.CustomerProfile;
import com.example.demo.service.CustomerProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer Profiles")
public class CustomerProfileController {
    
    private final CustomerProfileService customerProfileService;

    public CustomerProfileController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    @PostMapping
    @Operation(summary = "Create new customer")
    public ResponseEntity<CustomerProfile> createCustomer(@RequestBody CustomerProfile customer) {
        CustomerProfile created = customerProfileService.createCustomer(customer);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerProfile> getCustomerById(@Parameter(name = "id") @PathVariable Long id) {
        CustomerProfile customer = customerProfileService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    @Operation(summary = "Get all customers")
    public ResponseEntity<List<CustomerProfile>> getAllCustomers() {
        List<CustomerProfile> customers = customerProfileService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}/tier")
    @Operation(summary = "Update customer tier")
    public ResponseEntity<CustomerProfile> updateTier(
            @Parameter(name = "id") @PathVariable Long id,
            @Parameter(name = "newTier") @RequestParam String newTier) {
        CustomerProfile updated = customerProfileService.updateTier(id, newTier);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/lookup/{customerId}")
    @Operation(summary = "Find customer by customer ID")
    public ResponseEntity<CustomerProfile> findByCustomerId(@Parameter(name = "customerId") @PathVariable String customerId) {
        CustomerProfile customer = customerProfileService.findByCustomerId(customerId);
        return ResponseEntity.ok(customer);
    }
}