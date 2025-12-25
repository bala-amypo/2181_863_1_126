package com.example.demo.controller;

import com.example.demo.model.PurchaseRecord;
import com.example.demo.service.PurchaseRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@Tag(name = "Purchase Records")
public class PurchaseRecordController {
    
    private final PurchaseRecordService purchaseRecordService;
    
    public PurchaseRecordController(PurchaseRecordService purchaseRecordService) {
        this.purchaseRecordService = purchaseRecordService;
    }
    
    @PostMapping
    @Operation(summary = "Record purchase", description = "Record a new purchase")
    public ResponseEntity<PurchaseRecord> recordPurchase(@RequestBody PurchaseRecord purchase) {
        PurchaseRecord recorded = purchaseRecordService.recordPurchase(purchase);
        return ResponseEntity.ok(recorded);
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get purchases by customer", description = "Get all purchases for a customer")
    public ResponseEntity<List<PurchaseRecord>> getPurchasesByCustomer(@Parameter(name = "customerId", description = "Customer ID") @PathVariable Long customerId) {
        List<PurchaseRecord> purchases = purchaseRecordService.getPurchasesByCustomer(customerId);
        return ResponseEntity.ok(purchases);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get purchase by ID", description = "Get purchase record by ID")
    public ResponseEntity<PurchaseRecord> getPurchaseById(@Parameter(name = "id", description = "Purchase ID") @PathVariable Long id) {
        PurchaseRecord purchase = purchaseRecordService.getPurchaseById(id)
            .orElseThrow(() -> new RuntimeException("Purchase not found"));
        return ResponseEntity.ok(purchase);
    }
    
    @GetMapping
    @Operation(summary = "Get all purchases", description = "Get all purchase records")
    public ResponseEntity<List<PurchaseRecord>> getAllPurchases() {
        List<PurchaseRecord> purchases = purchaseRecordService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }
}