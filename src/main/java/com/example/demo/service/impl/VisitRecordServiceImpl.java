package com.example.demo.service.impl;

import com.example.demo.model.VisitRecord;
import com.example.demo.repository.VisitRecordRepository;
import com.example.demo.service.VisitRecordService;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitRecordServiceImpl implements VisitRecordService {
    
    private final VisitRecordRepository visitRecordRepository;
    
    public VisitRecordServiceImpl(VisitRecordRepository visitRecordRepository) {
        this.visitRecordRepository = visitRecordRepository;
    }
    
    @Override
    public VisitRecord recordVisit(VisitRecord visit) {
        if (visit.getChannel() != null && !Arrays.asList("STORE", "APP", "WEB").contains(visit.getChannel())) {
            throw new IllegalArgumentException("Invalid channel");
        }
        
        com.example.demo.entity.VisitRecord entity = convertToEntity(visit);
        com.example.demo.entity.VisitRecord saved = visitRecordRepository.save(entity);
        return convertToModel(saved);
    }
    
    @Override
    public List<VisitRecord> getVisitsByCustomer(Long customerId) {
        return visitRecordRepository.findByCustomerId(customerId).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<VisitRecord> getAllVisits() {
        return visitRecordRepository.findAll().stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }
    
    @Override
    public Optional<VisitRecord> getVisitById(Long id) {
        return visitRecordRepository.findById(id)
            .map(this::convertToModel);
    }
    
    private com.example.demo.entity.VisitRecord convertToEntity(VisitRecord model) {
        com.example.demo.entity.VisitRecord entity = new com.example.demo.entity.VisitRecord();
        entity.setId(model.getId());
        entity.setCustomerId(model.getCustomerId());
        entity.setVisitDate(model.getVisitDate());
        entity.setChannel(model.getChannel());
        return entity;
    }
    
    private VisitRecord convertToModel(com.example.demo.entity.VisitRecord entity) {
        VisitRecord model = new VisitRecord();
        model.setId(entity.getId());
        model.setCustomerId(entity.getCustomerId());
        model.setVisitDate(entity.getVisitDate());
        model.setChannel(entity.getChannel());
        return model;
    }
}