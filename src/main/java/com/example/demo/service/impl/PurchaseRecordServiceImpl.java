package com.example.demo.service.impl;

import com.example.demo.model.PurchaseRecord;
import com.example.demo.repository.PurchaseRecordRepository;
import com.example.demo.service.PurchaseRecordService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseRecordServiceImpl implements PurchaseRecordService {
    
    private final PurchaseRecordRepository purchaseRecordRepository;
    
    public PurchaseRecordServiceImpl(PurchaseRecordRepository purchaseRecordRepository) {
        this.purchaseRecordRepository = purchaseRecordRepository;
    }
    
    @Override
    public PurchaseRecord recordPurchase(PurchaseRecord purchase) {
        if (purchase.getAmount() != null && purchase.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        
        com.example.demo.entity.PurchaseRecord entity = convertToEntity(purchase);
        com.example.demo.entity.PurchaseRecord saved = purchaseRecordRepository.save(entity);
        return convertToModel(saved);
    }
    
    @Override
    public List<PurchaseRecord> getPurchasesByCustomer(Long customerId) {
        return purchaseRecordRepository.findByCustomerId(customerId).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<PurchaseRecord> getAllPurchases() {
        return purchaseRecordRepository.findAll().stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    @Override
    public Optional<PurchaseRecord> getPurchaseById(Long id) {
        return purchaseRecordRepository.findById(id)
            .map(this::convertToModel);
    }
    
    private com.example.demo.entity.PurchaseRecord convertToEntity(PurchaseRecord model) {
        com.example.demo.entity.PurchaseRecord entity = new com.example.demo.entity.PurchaseRecord();
        entity.setId(model.getId());
        entity.setCustomerId(model.getCustomerId());
        entity.setAmount(model.getAmount());
        entity.setPurchaseDate(model.getPurchaseDate());
        entity.setStoreLocation(model.getStoreLocation());
        return entity;
    }
    
    private PurchaseRecord convertToModel(com.example.demo.entity.PurchaseRecord entity) {
        PurchaseRecord model = new PurchaseRecord();
        model.setId(entity.getId());
        model.setCustomerId(entity.getCustomerId());
        model.setAmount(entity.getAmount());
        model.setPurchaseDate(entity.getPurchaseDate());
        model.setStoreLocation(entity.getStoreLocation());
        return model;
    }
}