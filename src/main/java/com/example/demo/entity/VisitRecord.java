package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "visit_records")
public class VisitRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long customerId;
    
    private LocalDate visitDate;
    
    private String channel;

    @ManyToOne
    @JoinColumn(name = "customerId", insertable = false, updatable = false)
    private CustomerProfile customer;

    public VisitRecord() {}

    public VisitRecord(Long customerId, LocalDate visitDate, String channel) {
        if (channel != null && !Arrays.asList("STORE", "APP", "WEB").contains(channel)) {
            throw new IllegalArgumentException("Invalid channel");
        }
        this.customerId = customerId;
        this.visitDate = visitDate;
        this.channel = channel;
    }

    @PrePersist
    @PreUpdate
    private void validateChannel() {
        if (channel != null && !Arrays.asList("STORE", "APP", "WEB").contains(channel)) {
            throw new IllegalArgumentException("Invalid channel");
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }
    
    public String getChannel() { return channel; }
    public void setChannel(String channel) { 
        if (channel != null && !Arrays.asList("STORE", "APP", "WEB").contains(channel)) {
            throw new IllegalArgumentException("Invalid channel");
        }
        this.channel = channel; 
    }
    
    public CustomerProfile getCustomer() { return customer; }
    public void setCustomer(CustomerProfile customer) { 
        this.customer = customer;
    }
}