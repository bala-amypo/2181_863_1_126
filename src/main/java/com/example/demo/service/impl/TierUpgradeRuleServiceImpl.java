package com.example.demo.service.impl;

import com.example.demo.entity.TierUpgradeRule;
import com.example.demo.repository.TierUpgradeRuleRepository;
import com.example.demo.service.TierUpgradeRuleService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TierUpgradeRuleServiceImpl implements TierUpgradeRuleService {
    private final TierUpgradeRuleRepository tierUpgradeRuleRepository;

    public TierUpgradeRuleServiceImpl(TierUpgradeRuleRepository tierUpgradeRuleRepository) {
        this.tierUpgradeRuleRepository = tierUpgradeRuleRepository;
    }

    @Override
    public TierUpgradeRule createRule(TierUpgradeRule rule) {
        if (rule.getMinSpend() < 0 || rule.getMinVisits() < 0) {
            throw new IllegalArgumentException("Minimum spend and visits must be >= 0");
        }
        return tierUpgradeRuleRepository.save(rule);
    }

    @Override
    public TierUpgradeRule updateRule(Long id, TierUpgradeRule updatedRule) {
        TierUpgradeRule rule = tierUpgradeRuleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rule not found"));
        rule.setFromTier(updatedRule.getFromTier());
        rule.setToTier(updatedRule.getToTier());
        rule.setMinSpend(updatedRule.getMinSpend());
        rule.setMinVisits(updatedRule.getMinVisits());
        rule.setActive(updatedRule.getActive());
        return tierUpgradeRuleRepository.save(rule);
    }

    @Override
    public List<TierUpgradeRule> getActiveRules() {
        return tierUpgradeRuleRepository.findByActiveTrue();
    }

    @Override
    public TierUpgradeRule getRule(String fromTier, String toTier) {
        return tierUpgradeRuleRepository.findByFromTierAndToTier(fromTier, toTier)
                .orElseThrow(() -> new NoSuchElementException("Rule not found"));
    }

    @Override
    public List<TierUpgradeRule> getAllRules() {
        return tierUpgradeRuleRepository.findAll();
    }
}