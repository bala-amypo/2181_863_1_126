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
        return ResponseEntity.ok(customerProfileService.createCustomer(customer));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerProfile> getCustomerById(@Parameter(name = "id") @PathVariable Long id) {
        return ResponseEntity.ok(customerProfileService.getCustomerById(id));
    }

    @GetMapping
    @Operation(summary = "Get all customers")
    public ResponseEntity<List<CustomerProfile>> getAllCustomers() {
        return ResponseEntity.ok(customerProfileService.getAllCustomers());
    }

    @PutMapping("/{id}/tier")
    @Operation(summary = "Update customer tier")
    public ResponseEntity<CustomerProfile> updateTier(@Parameter(name = "id") @PathVariable Long id, 
                                                     @Parameter(name = "newTier") @RequestParam String newTier) {
        return ResponseEntity.ok(customerProfileService.updateTier(id, newTier));
    }

    @GetMapping("/lookup/{customerId}")
    @Operation(summary = "Find customer by customer ID")
    public ResponseEntity<CustomerProfile> findByCustomerId(@Parameter(name = "customerId") @PathVariable String customerId) {
        return ResponseEntity.ok(customerProfileService.findByCustomerId(customerId));
    }
}