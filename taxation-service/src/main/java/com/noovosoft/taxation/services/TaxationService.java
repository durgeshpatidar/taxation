package com.noovosoft.taxation.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noovosoft.taxation.models.Taxation;
import com.noovosoft.taxation.repositories.TaxationRepository;

@Service
public class TaxationService {

    @Autowired
    private TaxationRepository repository;

    public Taxation saveTaxation(Taxation taxation) {
        return repository.save(taxation);
    }

    public Optional<Taxation> updateTaxation(UUID id, Taxation updated) {
        return repository.findById(id).map(existing -> {
            existing.setClientType(updated.getClientType());
            existing.setTaxType(updated.getTaxType());
            existing.setTaxableAmount(updated.getTaxableAmount());
            existing.setTaxAmount(updated.getTaxAmount());
            existing.setAssessmentDate(updated.getAssessmentDate());
            existing.setStatus(updated.getStatus());
            return repository.save(existing);
        });
    }

    public boolean deleteTaxation(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Taxation> getByClientId(UUID clientId) {
        return repository.findByClientId(clientId);
    }

    public List<Taxation> getByClientType(String clientType) {
        return repository.findByClientType(clientType);
    }

    public List<Taxation> getByTaxType(String taxType) {
        return repository.findByTaxType(taxType);
    }

    public List<Taxation> saveBulk(List<Taxation> taxations) {
        return repository.saveAll(taxations);
    }

    public double getTotalTaxPaid(UUID clientId) {
        return repository.findByClientId(clientId)
                .stream()
                .mapToDouble(Taxation::getTaxAmount)
                .sum();
    }
}