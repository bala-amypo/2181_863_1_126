package com.example.demo.controller;

import com.example.demo.entity.VisitRecord;
import com.example.demo.service.VisitRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
@Tag(name = "Visit Records")
public class VisitRecordController {
    
    private final VisitRecordService visitRecordService;

    public VisitRecordController(VisitRecordService visitRecordService) {
        this.visitRecordService = visitRecordService;
    }

    @PostMapping
    @Operation(summary = "Record new visit")
    public ResponseEntity<VisitRecord> recordVisit(@RequestBody VisitRecord visit) {
        VisitRecord recorded = visitRecordService.recordVisit(visit);
        return ResponseEntity.ok(recorded);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get visits by customer")
    public ResponseEntity<List<VisitRecord>> getVisitsByCustomer(@Parameter(name = "customerId") @PathVariable Long customerId) {
        List<VisitRecord> visits = visitRecordService.getVisitsByCustomer(customerId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get visit by ID")
    public ResponseEntity<VisitRecord> getVisitById(@Parameter(name = "id") @PathVariable Long id) {
        VisitRecord visit = visitRecordService.getVisitById(id);
        return ResponseEntity.ok(visit);
    }

    @GetMapping
    @Operation(summary = "Get all visits")
    public ResponseEntity<List<VisitRecord>> getAllVisits() {
        List<VisitRecord> visits = visitRecordService.getAllVisits();
        return ResponseEntity.ok(visits);
    }
}