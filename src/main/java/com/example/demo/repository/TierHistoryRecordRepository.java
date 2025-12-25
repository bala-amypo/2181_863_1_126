package com.example.demo.repository;

import com.example.demo.entity.TierHistoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TierHistoryRecordRepository extends JpaRepository<TierHistoryRecord, Long> {
    List<TierHistoryRecord> findByCustomerId(Long customerId);
    List<TierHistoryRecord> findByChangedAtBetween(LocalDateTime start, LocalDateTime end);
}