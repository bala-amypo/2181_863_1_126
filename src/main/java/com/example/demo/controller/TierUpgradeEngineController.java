package com.example.demo.controller;

import com.example.demo.entity.TierHistoryRecord;
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
    @Operation(summary = "Evaluate and upgrade customer tier")
    public ResponseEntity<TierHistoryRecord> evaluateAndUpgradeTier(@Parameter(name = "customerId") @PathVariable Long customerId) {
        TierHistoryRecord result = tierUpgradeEngineService.evaluateAndUpgradeTier(customerId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history/{customerId}")
    @Operation(summary = "Get tier history by customer")
    public ResponseEntity<List<TierHistoryRecord>> getHistoryByCustomer(@Parameter(name = "customerId") @PathVariable Long customerId) {
        return ResponseEntity.ok(tierUpgradeEngineService.getHistoryByCustomer(customerId));
    }

    @GetMapping
    @Operation(summary = "Get all tier history")
    public ResponseEntity<List<TierHistoryRecord>> getAllHistory() {
        return ResponseEntity.ok(tierUpgradeEngineService.getAllHistory());
    }
}