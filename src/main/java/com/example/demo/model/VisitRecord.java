package com.example.demo.model;

import java.time.LocalDate;

public class VisitRecord {
    private Long id;
    private CustomerProfile customer;
    private LocalDate visitDate;
    private String channel;

    public VisitRecord() {}

    public VisitRecord(CustomerProfile customer, LocalDate visitDate, String channel) {
        this.customer = customer;
        this.visitDate = visitDate;
        this.channel = channel;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public CustomerProfile getCustomer() { return customer; }
    public void setCustomer(CustomerProfile customer) { this.customer = customer; }
    
    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }
    
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
}