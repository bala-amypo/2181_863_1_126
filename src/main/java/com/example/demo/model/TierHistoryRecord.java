package com.example.demo.model;

import java.time.LocalDateTime;

public class TierHistoryRecord {
    private Long id;
    private Long customerId;
    private String oldTier;
    private String newTier;
    private String reason;
    private LocalDateTime changedAt;

    public TierHistoryRecord() {}

    public TierHistoryRecord(Long customerId, String oldTier, String newTier, String reason, LocalDateTime changedAt) {
        this.customerId = customerId;
        this.oldTier = oldTier;
        this.newTier = newTier;
        this.reason = reason;
        this.changedAt = changedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public String getOldTier() { return oldTier; }
    public void setOldTier(String oldTier) { this.oldTier = oldTier; }
    
    public String getNewTier() { return newTier; }
    public void setNewTier(String newTier) { this.newTier = newTier; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
}