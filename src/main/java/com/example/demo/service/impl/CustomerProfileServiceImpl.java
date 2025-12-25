package com.example.demo.service.impl;

import com.example.demo.model.CustomerProfile;
import com.example.demo.repository.CustomerProfileRepository;
import com.example.demo.service.CustomerProfileService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerProfileServiceImpl implements CustomerProfileService {
    
    private final CustomerProfileRepository customerProfileRepository;
    
    public CustomerProfileServiceImpl(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }
    
    @Override
    public CustomerProfile createCustomer(CustomerProfile customer) {
        if (customerProfileRepository.findByCustomerId(customer.getCustomerId()).isPresent()) {
            throw new IllegalArgumentException("Customer ID already exists");
        }
        
        if (customer.getCurrentTier() == null) {
            customer.setCurrentTier("BRONZE");
        }
        
        if (customer.getCreatedAt() == null) {
            customer.setCreatedAt(LocalDateTime.now());
        }
        
        com.example.demo.entity.CustomerProfile entity = convertToEntity(customer);
        com.example.demo.entity.CustomerProfile saved = customerProfileRepository.save(entity);
        return convertToModel(saved);
    }
    
    @Override
    public CustomerProfile getCustomerById(Long id) {
        com.example.demo.entity.CustomerProfile entity = customerProfileRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        return convertToModel(entity);
    }
    
    @Override
    public Optional<CustomerProfile> findByCustomerId(String customerId) {
        return customerProfileRepository.findByCustomerId(customerId)
            .map(this::convertToModel);
    }
    
    @Override
    public List<CustomerProfile> getAllCustomers() {
        return customerProfileRepository.findAll().stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    @Override
    public CustomerProfile updateTier(Long id, String newTier) {
        com.example.demo.entity.CustomerProfile entity = customerProfileRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        entity.setCurrentTier(newTier);
        com.example.demo.entity.CustomerProfile saved = customerProfileRepository.save(entity);
        return convertToModel(saved);
    }
    
    @Override
    public CustomerProfile updateStatus(Long id, boolean active) {
        com.example.demo.entity.CustomerProfile entity = customerProfileRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        entity.setActive(active);
        com.example.demo.entity.CustomerProfile saved = customerProfileRepository.save(entity);
        return convertToModel(saved);
    }
    
    private com.example.demo.entity.CustomerProfile convertToEntity(CustomerProfile model) {
        com.example.demo.entity.CustomerProfile entity = new com.example.demo.entity.CustomerProfile();
        entity.setId(model.getId());
        entity.setCustomerId(model.getCustomerId());
        entity.setFullName(model.getFullName());
        entity.setEmail(model.getEmail());
        entity.setPhone(model.getPhone());
        entity.setCurrentTier(model.getCurrentTier());
        entity.setActive(model.getActive());
        entity.setCreatedAt(model.getCreatedAt());
        return entity;
    }
    
    private CustomerProfile convertToModel(com.example.demo.entity.CustomerProfile entity) {
        CustomerProfile model = new CustomerProfile();
        model.setId(entity.getId());
        model.setCustomerId(entity.getCustomerId());
        model.setFullName(entity.getFullName());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        model.setCurrentTier(entity.getCurrentTier());
        model.setActive(entity.getActive());
        model.setCreatedAt(entity.getCreatedAt());
        return model;
    }
}