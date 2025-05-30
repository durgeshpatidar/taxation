package com.noovosoft.business.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noovosoft.business.client.Taxation;
import com.noovosoft.business.client.TaxationClient;
import com.noovosoft.business.models.Business;
import com.noovosoft.business.repositories.BusinessRepository;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository repository;
    
    @Autowired
    private TaxationClient taxationClient;

    public void fileBusinessTax(UUID clientId, double revenue, String taxSubType) {
        Taxation tax = new Taxation();
        tax.setClientId(clientId);
        tax.setClientType("Business");
        tax.setTaxType(taxSubType); // e.g., GST, Corporate Tax
        tax.setTaxableAmount(revenue);
        tax.setTaxAmount(revenue * 0.15); // Example 15% tax
        tax.setAssessmentDate(LocalDate.now());
        tax.setStatus("Filed");

        taxationClient.fileTax(tax);
    }

    public double getTotalTaxPaid(UUID clientId) {
        return taxationClient.getTotalTaxPaid(clientId);
    }


    public Business saveBusiness(Business business) {
        return repository.save(business);
    }

    public List<Business> getBusinessesByClient(UUID clientId) {
        return repository.findByClientId(clientId);
    }

    public Optional<Business> updateBusiness(UUID id, Business updated) {
        return repository.findById(id).map(existing -> {
            existing.setBusinessName(updated.getBusinessName());
            existing.setGstNumber(updated.getGstNumber());
            existing.setAnnualTurnover(updated.getAnnualTurnover());
            existing.setRegistrationDate(updated.getRegistrationDate());
            return repository.save(existing);
        });
    }

    public boolean deleteBusiness(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Business> findByGstNumber(String gstNumber) {
        return repository.findByGstNumber(gstNumber);
    }

    public List<Business> saveBulk(List<Business> businesses) {
        return repository.saveAll(businesses);
    }

    public double getTotalTurnover(UUID clientId) {
        return repository.findByClientId(clientId)
                .stream()
                .mapToDouble(Business::getAnnualTurnover)
                .sum();
    }
}
