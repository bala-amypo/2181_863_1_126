package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "visit_records")
public class VisitRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long customerId;
    
    @Column(nullable = false)
    private LocalDate visitDate;
    
    @Column(nullable = false)
    private String channel;
    
    private static final List<String> VALID_CHANNELS = Arrays.asList("STORE", "APP", "WEB");

    public VisitRecord() {}

    public VisitRecord(Long customerId, LocalDate visitDate, String channel) {
        if (channel != null && !VALID_CHANNELS.contains(channel)) {
            throw new IllegalArgumentException("Invalid channel");
        }
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
    public void setChannel(String channel) { 
        if (channel != null && !VALID_CHANNELS.contains(channel)) {
            throw new IllegalArgumentException("Invalid channel");
        }
        this.channel = channel; 
    }
}