package com.example.demo.model;

import java.time.LocalDate;

public class PurchaseRecordModel {
    private Long id;
    private Long customerId;
    private Double amount;
    private LocalDate purchaseDate;
    private String storeLocation;

    // Optional: CustomerProfile DTO reference
    private CustomerProfile customer;

    // Getters and setters
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
    public void setCustomer(CustomerProfile customer) { this.customer = customer; }
}
