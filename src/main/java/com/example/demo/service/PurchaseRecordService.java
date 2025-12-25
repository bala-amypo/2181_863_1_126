package com.example.demo.service;

import com.example.demo.model.PurchaseRecordModel;
import java.util.List;
import java.util.Optional;

public interface PurchaseRecordService {
    PurchaseRecordModel recordPurchase(PurchaseRecordModel purchase);
    List<PurchaseRecordModel> getPurchasesByCustomer(Long customerId);
    List<PurchaseRecordModel> getAllPurchases();
    Optional<PurchaseRecordModel> getPurchaseById(Long id);
}
