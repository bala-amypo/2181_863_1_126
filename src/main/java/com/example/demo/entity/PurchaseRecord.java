package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_records")
public class PurchaseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerProfile customer;

    private Double amount;
    private LocalDate purchaseDate;
    private String storeLocation;

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
