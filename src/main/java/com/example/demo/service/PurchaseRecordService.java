package com.example.demo.service;

import com.example.demo.entity.PurchaseRecord;
import java.util.List;
import java.util.Optional;

public interface PurchaseRecordService {
    PurchaseRecord recordPurchase(PurchaseRecord purchase);
    List<PurchaseRecord> getPurchasesByCustomer(Long customerId);
    List<PurchaseRecord> getAllPurchases();
    PurchaseRecord getPurchaseById(Long id);
}