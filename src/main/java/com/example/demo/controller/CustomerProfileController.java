package com.example.demo.controller;

import com.example.demo.model.CustomerProfile;
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
    @Operation(summary = "Create customer", description = "Create a new customer profile")
    public ResponseEntity<CustomerProfile> createCustomer(@RequestBody CustomerProfile customer) {
        CustomerProfile created = customerProfileService.createCustomer(customer);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieve customer profile by ID")
    public ResponseEntity<CustomerProfile> getCustomerById(@Parameter(name = "id", description = "Customer ID") @PathVariable Long id) {
        CustomerProfile customer = customerProfileService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    
    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieve all customer profiles")
    public ResponseEntity<List<CustomerProfile>> getAllCustomers() {
        List<CustomerProfile> customers = customerProfileService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @PutMapping("/{id}/tier")
    @Operation(summary = "Update customer tier", description = "Update customer's loyalty tier")
    public ResponseEntity<CustomerProfile> updateTier(@Parameter(name = "id", description = "Customer ID") @PathVariable Long id, 
                                                     @Parameter(name = "newTier", description = "New tier level") @RequestParam String newTier) {
        CustomerProfile updated = customerProfileService.updateTier(id, newTier);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/lookup/{customerId}")
    @Operation(summary = "Find customer by customer ID", description = "Find customer by customer identifier")
    public ResponseEntity<CustomerProfile> findByCustomerId(@Parameter(name = "customerId", description = "Customer identifier") @PathVariable String customerId) {
        CustomerProfile customer = customerProfileService.findByCustomerId(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        return ResponseEntity.ok(customer);
    }
}