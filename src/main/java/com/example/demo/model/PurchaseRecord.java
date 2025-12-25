package com.example.demo.model;

import java.time.LocalDate;

public class PurchaseRecord {
    private Long id;
    private Long customerId;
    private Double amount;
    private LocalDate purchaseDate;
    private String storeLocation;
    private CustomerProfile customer;
    
    public PurchaseRecord() {}
    
    public PurchaseRecord(Long customerId, Double amount, LocalDate purchaseDate, String storeLocation) {
        this.customerId = customerId;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.storeLocation = storeLocation;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    
    public String getStoreLocation() { return storeLocation; }
    public void setStoreLocation(String storeLocation) { this.storeLocation = storeLocation; }
    
    public CustomerProfile getCustomer() { return customer; }
    public void setCustomer(CustomerProfile customer) { 
        this.customer = customer;
        if (customer != null) {
            this.customerId = customer.getId();
        }
    }
}