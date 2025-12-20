package com.example.demo.service.impl;

import com.example.demo.entity.CustomerProfile;
import com.example.demo.repository.CustomerProfileRepository;
import com.example.demo.service.CustomerProfileService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

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
        return customerProfileRepository.save(customer);
    }

    @Override
    public CustomerProfile getCustomerById(Long id) {
        return customerProfileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    @Override
    public CustomerProfile findByCustomerId(String customerId) {
        return customerProfileRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    @Override
    public List<CustomerProfile> getAllCustomers() {
        return customerProfileRepository.findAll();
    }

    @Override
    public CustomerProfile updateTier(Long id, String newTier) {
        CustomerProfile customer = customerProfileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        customer.setCurrentTier(newTier);
        return customerProfileRepository.save(customer);
    }

    @Override
    public CustomerProfile updateStatus(Long id, boolean active) {
        CustomerProfile customer = customerProfileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        customer.setActive(active);
        return customerProfileRepository.save(customer);
    }
}