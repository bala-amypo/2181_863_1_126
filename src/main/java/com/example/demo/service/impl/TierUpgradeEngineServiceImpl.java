package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.TierUpgradeEngineService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TierUpgradeEngineServiceImpl implements TierUpgradeEngineService {
    
    private final CustomerProfileRepository customerProfileRepository;
    private final PurchaseRecordRepository purchaseRecordRepository;
    private final VisitRecordRepository visitRecordRepository;
    private final TierUpgradeRuleRepository tierUpgradeRuleRepository;
    private final TierHistoryRecordRepository tierHistoryRecordRepository;

    public TierUpgradeEngineServiceImpl(
            CustomerProfileRepository customerProfileRepository,
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
        CustomerProfile customer = customerProfileRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        List<PurchaseRecord> purchases = purchaseRecordRepository.findByCustomerId(customerId);
        List<VisitRecord> visits = visitRecordRepository.findByCustomerId(customerId);

        double totalSpend = purchases.stream()
                .mapToDouble(p -> p.getAmount() != null ? p.getAmount() : 0.0)
                .sum();
        
        int totalVisits = visits.size();
        String currentTier = customer.getCurrentTier();

        List<TierUpgradeRule> activeRules = tierUpgradeRuleRepository.findByActiveTrue();
        
        for (TierUpgradeRule rule : activeRules) {
            if (currentTier.equals(rule.getFromTier())) {
                boolean spendMet = rule.getMinSpend() == null || totalSpend >= rule.getMinSpend();
                boolean visitsMet = rule.getMinVisits() == null || totalVisits >= rule.getMinVisits();
                
                if (spendMet && visitsMet) {
                    String oldTier = customer.getCurrentTier();
                    customer.setCurrentTier(rule.getToTier());
                    customerProfileRepository.save(customer);
                    
                    TierHistoryRecord history = new TierHistoryRecord();
                    history.setCustomerId(customerId);
                    history.setOldTier(oldTier);
                    history.setNewTier(rule.getToTier());
                    history.setReason("Automatic upgrade based on spend and visits");
                    
                    return tierHistoryRecordRepository.save(history);
                }
            }
        }
        
        return null;
    }

    @Override
    public List<TierHistoryRecord> getHistoryByCustomer(Long customerId) {
        return tierHistoryRecordRepository.findByCustomerId(customerId);
    }

    @Override
    public List<TierHistoryRecord> getAllHistory() {
        return tierHistoryRecordRepository.findAll();
    }
}