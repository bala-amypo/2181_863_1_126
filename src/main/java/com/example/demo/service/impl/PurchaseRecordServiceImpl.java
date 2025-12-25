package com.example.demo.service.impl;

import com.example.demo.model.PurchaseRecordModel;
import com.example.demo.repository.PurchaseRecordRepository;
import com.example.demo.repository.CustomerProfileRepository;
import com.example.demo.entity.PurchaseRecord;
import com.example.demo.entity.CustomerProfile;
import com.example.demo.service.PurchaseRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseRecordServiceImpl implements PurchaseRecordService {

    private final PurchaseRecordRepository purchaseRecordRepository;
    private final CustomerProfileRepository customerProfileRepository;

    public PurchaseRecordServiceImpl(PurchaseRecordRepository purchaseRecordRepository,
                                     CustomerProfileRepository customerProfileRepository) {
        this.purchaseRecordRepository = purchaseRecordRepository;
        this.customerProfileRepository = customerProfileRepository;
    }

    @Override
    public PurchaseRecordModel recordPurchase(PurchaseRecordModel purchase) {
        if (purchase.getAmount() != null && purchase.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        CustomerProfile customer = customerProfileRepository.findById(purchase.getCustomer().getId())
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        PurchaseRecord entity = convertToEntity(purchase, customer);
        PurchaseRecord saved = purchaseRecordRepository.save(entity);
        return convertToModel(saved);
    }

    @Override
    public List<PurchaseRecordModel> getPurchasesByCustomer(Long customerId) {
        return purchaseRecordRepository.findByCustomerId(customerId).stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRecordModel> getAllPurchases() {
        return purchaseRecordRepository.findAll().stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PurchaseRecordModel> getPurchaseById(Long id) {
        return purchaseRecordRepository.findById(id)
                .map(this::convertToModel);
    }

    private PurchaseRecord convertToEntity(PurchaseRecordModel model, CustomerProfile customer) {
        PurchaseRecord entity = new PurchaseRecord();
        entity.setId(model.getId());
        entity.setCustomer(customer);
        entity.setAmount(model.getAmount());
        entity.setPurchaseDate(model.getPurchaseDate());
        entity.setStoreLocation(model.getStoreLocation());
        return entity;
    }

    private PurchaseRecordModel convertToModel(PurchaseRecord entity) {
        PurchaseRecordModel model = new PurchaseRecordModel();
        model.setId(entity.getId());
        model.setCustomer(entity.getCustomer());
        model.setAmount(entity.getAmount());
        model.setPurchaseDate(entity.getPurchaseDate());
        model.setStoreLocation(entity.getStoreLocation());
        return model;
    }
}
