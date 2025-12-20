package com.example.demo.controller;

import com.example.demo.entity.PurchaseRecord;
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
    @Operation(summary = "Record new purchase")
    public ResponseEntity<PurchaseRecord> recordPurchase(@RequestBody PurchaseRecord purchase) {
        return ResponseEntity.ok(purchaseRecordService.recordPurchase(purchase));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get purchases by customer")
    public ResponseEntity<List<PurchaseRecord>> getPurchasesByCustomer(@Parameter(name = "customerId") @PathVariable Long customerId) {
        return ResponseEntity.ok(purchaseRecordService.getPurchasesByCustomer(customerId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get purchase by ID")
    public ResponseEntity<PurchaseRecord> getPurchaseById(@Parameter(name = "id") @PathVariable Long id) {
        return ResponseEntity.ok(purchaseRecordService.getPurchaseById(id));
    }

    @GetMapping
    @Operation(summary = "Get all purchases")
    public ResponseEntity<List<PurchaseRecord>> getAllPurchases() {
        return ResponseEntity.ok(purchaseRecordService.getAllPurchases());
    }
}