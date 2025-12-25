package com.example.demo.model;

import java.time.LocalDate;

public class VisitRecord {
    private Long id;
    private Long customerId;
    private LocalDate visitDate;
    private String channel;
    private CustomerProfile customer;
    
    public VisitRecord() {}
    
    public VisitRecord(Long customerId, LocalDate visitDate, String channel) {
        this.customerId = customerId;
        this.visitDate = visitDate;
        this.channel = channel;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }
    
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    
    public CustomerProfile getCustomer() { return customer; }
    public void setCustomer(CustomerProfile customer) { 
        this.customer = customer;
        if (customer != null) {
            this.customerId = customer.getId();
        }
    }
}