package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseRecord;
import com.example.demo.repository.PurchaseRecordRepository;
import com.example.demo.service.PurchaseRecordService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PurchaseRecordServiceImpl implements PurchaseRecordService {
    
    private final PurchaseRecordRepository purchaseRecordRepository;

    public PurchaseRecordServiceImpl(PurchaseRecordRepository purchaseRecordRepository) {
        this.purchaseRecordRepository = purchaseRecordRepository;
    }

    @Override
    public PurchaseRecord recordPurchase(PurchaseRecord purchase) {
        if (purchase.getAmount() == null || purchase.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return purchaseRecordRepository.save(purchase);
    }

    @Override
    public List<PurchaseRecord> getPurchasesByCustomer(Long customerId) {
        return purchaseRecordRepository.findByCustomerId(customerId);
    }

    @Override
    public List<PurchaseRecord> getAllPurchases() {
        return purchaseRecordRepository.findAll();
    }

    @Override
    public PurchaseRecord getPurchaseById(Long id) {
        return purchaseRecordRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Purchase record not found"));
    }
}