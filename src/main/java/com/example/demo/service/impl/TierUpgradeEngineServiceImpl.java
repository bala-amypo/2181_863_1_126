package com.example.demo.service.impl;

import com.example.demo.model.TierHistoryRecord;
import com.example.demo.repository.*;
import com.example.demo.service.TierUpgradeEngineService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TierUpgradeEngineServiceImpl implements TierUpgradeEngineService {
    
    private final CustomerProfileRepository customerProfileRepository;
    private final PurchaseRecordRepository purchaseRecordRepository;
    private final VisitRecordRepository visitRecordRepository;
    private final TierUpgradeRuleRepository tierUpgradeRuleRepository;
    private final TierHistoryRecordRepository tierHistoryRecordRepository;
    
    public TierUpgradeEngineServiceImpl(CustomerProfileRepository customerProfileRepository,
                                       PurchaseRecordRepository purchaseRecordRepository,
                                       VisitRecordRepository visitRecordRepository,
                                       TierUpgradeRuleRepository tierUpgradeRuleRepository,
                                       TierHistoryRecordRepository tierHistoryRecordRepository) {
        this.customerProfileRepository = customerProfileRepository;
        this.purchaseRecordRepository = purchaseRecordRepository;
        this.visitRecordRepository = visitRecordRepository;
        this.tierUpgradeRuleRepository = tierUpgradeRuleRepository;
        this.tierHistoryRecordRepository = tierHistoryRecordRepository;
    }
    
    @Override
    public TierHistoryRecord evaluateAndUpgradeTier(Long customerId) {
        com.example.demo.entity.CustomerProfile customer = customerProfileRepository.findById(customerId)
            .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        
        double totalSpend = purchaseRecordRepository.findByCustomerId(customerId).stream()
            .mapToDouble(p -> p.getAmount() != null ? p.getAmount() : 0.0)
            .sum();
        
        long totalVisits = visitRecordRepository.findByCustomerId(customerId).size();
        
        String currentTier = customer.getCurrentTier();
        
        List<com.example.demo.entity.TierUpgradeRule> activeRules = tierUpgradeRuleRepository.findByActiveTrue()
            .stream()
            .filter(rule -> currentTier.equals(rule.getFromTier()))
            .collect(Collectors.toList());
        
        for (com.example.demo.entity.TierUpgradeRule rule : activeRules) {
            boolean spendMet = rule.getMinSpend() == null || totalSpend >= rule.getMinSpend();
            boolean visitsMet = rule.getMinVisits() == null || totalVisits >= rule.getMinVisits();
            
            if (spendMet && visitsMet) {
                String oldTier = customer.getCurrentTier();
                customer.setCurrentTier(rule.getToTier());
                customerProfileRepository.save(customer);
                
                com.example.demo.entity.TierHistoryRecord historyEntity = new com.example.demo.entity.TierHistoryRecord();
                historyEntity.setCustomerId(customerId);
                historyEntity.setOldTier(oldTier);
                historyEntity.setNewTier(rule.getToTier());
                historyEntity.setReason("Automatic upgrade based on spend and visits");
                historyEntity.setChangedAt(LocalDateTime.now());
                
                com.example.demo.entity.TierHistoryRecord saved = tierHistoryRecordRepository.save(historyEntity);
                return convertToModel(saved);
            }
        }
        
        return null;
    }
    
    @Override
    public List<TierHistoryRecord> getHistoryByCustomer(Long customerId) {
        return tierHistoryRecordRepository.findByCustomerId(customerId).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<TierHistoryRecord> getAllHistory() {
        return tierHistoryRecordRepository.findAll().stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    private TierHistoryRecord convertToModel(com.example.demo.entity.TierHistoryRecord entity) {
        TierHistoryRecord model = new TierHistoryRecord();
        model.setId(entity.getId());
        model.setCustomerId(entity.getCustomerId());
        model.setOldTier(entity.getOldTier());
        model.setNewTier(entity.getNewTier());
        model.setReason(entity.getReason());
        model.setChangedAt(entity.getChangedAt());
        return model;
    }
}