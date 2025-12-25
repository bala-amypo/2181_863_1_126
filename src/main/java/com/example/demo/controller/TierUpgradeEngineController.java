package com.example.demo.controller;

import com.example.demo.model.TierHistoryRecord;
import com.example.demo.service.TierUpgradeEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tier-engine")
@Tag(name = "Tier Upgrade Engine")
public class TierUpgradeEngineController {
    
    private final TierUpgradeEngineService tierUpgradeEngineService;
    
    public TierUpgradeEngineController(TierUpgradeEngineService tierUpgradeEngineService) {
        this.tierUpgradeEngineService = tierUpgradeEngineService;
    }
    
    @PostMapping("/evaluate/{customerId}")
    @Operation(summary = "Evaluate tier upgrade", description = "Evaluate and upgrade customer tier if eligible")
    public ResponseEntity<TierHistoryRecord> evaluateAndUpgradeTier(@Parameter(name = "customerId", description = "Customer ID") @PathVariable Long customerId) {
        TierHistoryRecord result = tierUpgradeEngineService.evaluateAndUpgradeTier(customerId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/history/{customerId}")
    @Operation(summary = "Get customer tier history", description = "Get tier upgrade history for a customer")
    public ResponseEntity<List<TierHistoryRecord>> getHistoryByCustomer(@Parameter(name = "customerId", description = "Customer ID") @PathVariable Long customerId) {
        List<TierHistoryRecord> history = tierUpgradeEngineService.getHistoryByCustomer(customerId);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping
    @Operation(summary = "Get all tier history", description = "Get all tier upgrade history records")
    public ResponseEntity<List<TierHistoryRecord>> getAllHistory() {
        List<TierHistoryRecord> history = tierUpgradeEngineService.getAllHistory();
        return ResponseEntity.ok(history);
    }
}