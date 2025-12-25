package com.example.demo.service;

import com.example.demo.model.TierHistoryRecord;
import java.util.List;

public interface TierUpgradeEngineService {
    TierHistoryRecord evaluateAndUpgradeTier(Long customerId);
    List<TierHistoryRecord> getHistoryByCustomer(Long customerId);
    List<TierHistoryRecord> getAllHistory();
}