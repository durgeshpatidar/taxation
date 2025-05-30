package com.noovosoft.salaried.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noovosoft.salaried.client.Taxation;
import com.noovosoft.salaried.client.TaxationClient;
import com.noovosoft.salaried.models.Salary;
import com.noovosoft.salaried.repositories.SalaryRepository;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository repository;
    
    @Autowired
    private TaxationClient taxationClient;

    public void fileIncomeTaxForSalaried(UUID clientId, double income) {
        Taxation tax = new Taxation();
        tax.setClientId(clientId);
        tax.setClientType("Salaried");
        tax.setTaxType("Income Tax");
        tax.setTaxableAmount(income);
        tax.setTaxAmount(income * 0.10); // example: 10% flat
        tax.setAssessmentDate(LocalDate.now());
        tax.setStatus("Filed");

        taxationClient.fileTax(tax);
    }

    public double getTotalTaxPaid(UUID clientId) {
        return taxationClient.getTotalTaxPaid(clientId);
    }
    
    public Salary saveSalary(Salary salary) {
        return repository.save(salary);
    }

    public List<Salary> getSalariesByClient(UUID clientId) {
        return repository.findByClientId(clientId);
    }

    public Optional<Salary> updateSalary(UUID salaryId, Salary updated) {
        return repository.findById(salaryId).map(existing -> {
            existing.setMonthlyIncome(updated.getMonthlyIncome());
            existing.setEmployer(updated.getEmployer());
            existing.setSalaryDate(updated.getSalaryDate());
            return repository.save(existing);
        });
    }

    public boolean deleteSalary(UUID salaryId) {
        if (repository.existsById(salaryId)) {
            repository.deleteById(salaryId);
            return true;
        }
        return false;
    }

    public Map<String, Double> getYearlySummary(UUID clientId) {
        List<Salary> salaries = repository.findByClientId(clientId);
        return salaries.stream()
                .collect(Collectors.groupingBy(
                    s -> String.valueOf(s.getSalaryDate().getYear()),
                    Collectors.summingDouble(Salary::getMonthlyIncome)
                ));
    }

    public List<Salary> getSalariesByEmployer(String employer) {
        return repository.findByEmployer(employer);
    }

    public List<Salary> saveBulk(List<Salary> salaryList) {
        return repository.saveAll(salaryList);
    }

    public double calculateTotalIncome(UUID clientId) {
        return repository.findByClientId(clientId)
                .stream()
                .mapToDouble(Salary::getMonthlyIncome)
                .sum();
    }
}
