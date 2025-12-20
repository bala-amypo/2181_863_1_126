package com.example.demo.controller;

import com.example.demo.entity.TierUpgradeRule;
import com.example.demo.service.TierUpgradeRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tier-rules")
@Tag(name = "Tier Upgrade Rules")
public class TierUpgradeRuleController {
    private final TierUpgradeRuleService tierUpgradeRuleService;

    public TierUpgradeRuleController(TierUpgradeRuleService tierUpgradeRuleService) {
        this.tierUpgradeRuleService = tierUpgradeRuleService;
    }

    @PostMapping
    @Operation(summary = "Create new tier upgrade rule")
    public ResponseEntity<TierUpgradeRule> createRule(@RequestBody TierUpgradeRule rule) {
        return ResponseEntity.ok(tierUpgradeRuleService.createRule(rule));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update tier upgrade rule")
    public ResponseEntity<TierUpgradeRule> updateRule(@Parameter(name = "id") @PathVariable Long id, 
                                                      @RequestBody TierUpgradeRule rule) {
        return ResponseEntity.ok(tierUpgradeRuleService.updateRule(id, rule));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active tier upgrade rules")
    public ResponseEntity<List<TierUpgradeRule>> getActiveRules() {
        return ResponseEntity.ok(tierUpgradeRuleService.getActiveRules());
    }

    @GetMapping
    @Operation(summary = "Get all tier upgrade rules")
    public ResponseEntity<List<TierUpgradeRule>> getAllRules() {
        return ResponseEntity.ok(tierUpgradeRuleService.getAllRules());
    }

    @GetMapping("/lookup")
    @Operation(summary = "Lookup tier upgrade rule")
    public ResponseEntity<TierUpgradeRule> getRule(@Parameter(name = "fromTier") @RequestParam String fromTier, 
                                                   @Parameter(name = "toTier") @RequestParam String toTier) {
        return ResponseEntity.ok(tierUpgradeRuleService.getRule(fromTier, toTier));
    }
}