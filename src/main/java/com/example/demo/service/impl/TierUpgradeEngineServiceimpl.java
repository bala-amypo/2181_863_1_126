package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.TierUpgradeEngineService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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
        CustomerProfile customer = customerProfileRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        List<PurchaseRecord> purchases = purchaseRecordRepository.findByCustomerId(customerId);
        double totalSpend = purchases.stream().mapToDouble(PurchaseRecord::getAmount).sum();

        List<VisitRecord> visits = visitRecordRepository.findByCustomerId(customerId);
        int totalVisits = visits.size();

        String currentTier = customer.getCurrentTier();
        List<TierUpgradeRule> activeRules = tierUpgradeRuleRepository.findByActiveTrue();

        for (TierUpgradeRule rule : activeRules) {
            if (rule.getFromTier().equals(currentTier) && 
                totalSpend >= rule.getMinSpend() && 
                totalVisits >= rule.getMinVisits()) {
                
                String oldTier = customer.getCurrentTier();
                customer.setCurrentTier(rule.getToTier());
                customerProfileRepository.save(customer);

                TierHistoryRecord historyRecord = new TierHistoryRecord(
                    customerId, oldTier, rule.getToTier(), 
                    "Automatic upgrade based on spend and visits", 
                    LocalDateTime.now()
                );
                return tierHistoryRecordRepository.save(historyRecord);
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