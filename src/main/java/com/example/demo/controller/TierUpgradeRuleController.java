package com.example.demo.controller;

import com.example.demo.model.TierUpgradeRule;
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
    @Operation(summary = "Create tier rule", description = "Create a new tier upgrade rule")
    public ResponseEntity<TierUpgradeRule> createRule(@RequestBody TierUpgradeRule rule) {
        TierUpgradeRule created = tierUpgradeRuleService.createRule(rule);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update tier rule", description = "Update an existing tier upgrade rule")
    public ResponseEntity<TierUpgradeRule> updateRule(@Parameter(name = "id", description = "Rule ID") @PathVariable Long id, 
                                                     @RequestBody TierUpgradeRule rule) {
        TierUpgradeRule updated = tierUpgradeRuleService.updateRule(id, rule);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get active rules", description = "Get all active tier upgrade rules")
    public ResponseEntity<List<TierUpgradeRule>> getActiveRules() {
        List<TierUpgradeRule> rules = tierUpgradeRuleService.getActiveRules();
        return ResponseEntity.ok(rules);
    }
    
    @GetMapping
    @Operation(summary = "Get all rules", description = "Get all tier upgrade rules")
    public ResponseEntity<List<TierUpgradeRule>> getAllRules() {
        List<TierUpgradeRule> rules = tierUpgradeRuleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
    
    @GetMapping("/lookup")
    @Operation(summary = "Get rule by tiers", description = "Get tier upgrade rule by from and to tiers")
    public ResponseEntity<TierUpgradeRule> getRule(@Parameter(name = "fromTier", description = "From tier") @RequestParam String fromTier, 
                                                  @Parameter(name = "toTier", description = "To tier") @RequestParam String toTier) {
        TierUpgradeRule rule = tierUpgradeRuleService.getRule(fromTier, toTier)
            .orElseThrow(() -> new RuntimeException("Rule not found"));
        return ResponseEntity.ok(rule);
    }
}