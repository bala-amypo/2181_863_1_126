package com.example.demo.model;

import java.time.LocalDate;
import com.example.demo.entity.CustomerProfile;

public class PurchaseRecord {

    private Long id;
    private CustomerProfile customer;
    private Double amount;
    private LocalDate purchaseDate;
    private String storeLocation;

    public PurchaseRecord() {}

    public PurchaseRecord(CustomerProfile customer, Double amount, LocalDate purchaseDate, String storeLocation) {
        this.customer = customer;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.storeLocation = storeLocation;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CustomerProfile getCustomer() { return customer; }
    public void setCustomer(CustomerProfile customer) { this.customer = customer; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getStoreLocation() { return storeLocation; }
    public void setStoreLocation(String storeLocation) { this.storeLocation = storeLocation; }
}
