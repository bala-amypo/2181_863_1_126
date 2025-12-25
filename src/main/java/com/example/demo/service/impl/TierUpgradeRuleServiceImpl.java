package com.example.demo.service.impl;

import com.example.demo.model.TierUpgradeRule;
import com.example.demo.repository.TierUpgradeRuleRepository;
import com.example.demo.service.TierUpgradeRuleService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TierUpgradeRuleServiceImpl implements TierUpgradeRuleService {
    
    private final TierUpgradeRuleRepository tierUpgradeRuleRepository;
    
    public TierUpgradeRuleServiceImpl(TierUpgradeRuleRepository tierUpgradeRuleRepository) {
        this.tierUpgradeRuleRepository = tierUpgradeRuleRepository;
    }
    
    @Override
    public TierUpgradeRule createRule(TierUpgradeRule rule) {
        if ((rule.getMinSpend() != null && rule.getMinSpend() < 0) || 
            (rule.getMinVisits() != null && rule.getMinVisits() < 0)) {
            throw new IllegalArgumentException("Min spend and min visits must be >= 0");
        }
        
        com.example.demo.entity.TierUpgradeRule entity = convertToEntity(rule);
        com.example.demo.entity.TierUpgradeRule saved = tierUpgradeRuleRepository.save(entity);
        return convertToModel(saved);
    }
    
    @Override
    public TierUpgradeRule updateRule(Long id, TierUpgradeRule updatedRule) {
        com.example.demo.entity.TierUpgradeRule entity = tierUpgradeRuleRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Rule not found"));
        
        entity.setFromTier(updatedRule.getFromTier());
        entity.setToTier(updatedRule.getToTier());
        entity.setMinSpend(updatedRule.getMinSpend());
        entity.setMinVisits(updatedRule.getMinVisits());
        entity.setActive(updatedRule.getActive());
        
        com.example.demo.entity.TierUpgradeRule saved = tierUpgradeRuleRepository.save(entity);
        return convertToModel(saved);
    }
    
    @Override
    public List<TierUpgradeRule> getActiveRules() {
        return tierUpgradeRuleRepository.findByActiveTrue().stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    @Override
    public Optional<TierUpgradeRule> getRule(String fromTier, String toTier) {
        return tierUpgradeRuleRepository.findByFromTierAndToTier(fromTier, toTier)
            .map(this::convertToModel);
    }
    
    @Override
    public List<TierUpgradeRule> getAllRules() {
        return tierUpgradeRuleRepository.findAll().stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    private com.example.demo.entity.TierUpgradeRule convertToEntity(TierUpgradeRule model) {
        com.example.demo.entity.TierUpgradeRule entity = new com.example.demo.entity.TierUpgradeRule();
        entity.setId(model.getId());
        entity.setFromTier(model.getFromTier());
        entity.setToTier(model.getToTier());
        entity.setMinSpend(model.getMinSpend());
        entity.setMinVisits(model.getMinVisits());
        entity.setActive(model.getActive());
        return entity;
    }
    
    private TierUpgradeRule convertToModel(com.example.demo.entity.TierUpgradeRule entity) {
        TierUpgradeRule model = new TierUpgradeRule();
        model.setId(entity.getId());
        model.setFromTier(entity.getFromTier());
        model.setToTier(entity.getToTier());
        model.setMinSpend(entity.getMinSpend());
        model.setMinVisits(entity.getMinVisits());
        model.setActive(entity.getActive());
        return model;
    }
}