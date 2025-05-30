package com.noovosoft.salaried.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noovosoft.salaried.models.Salary;
import com.noovosoft.salaried.services.SalaryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    @Autowired
    private SalaryService service;
    
    @PostMapping("/tax-file")
    public ResponseEntity<String> fileIncomeTax(
            @RequestParam UUID clientId,
            @RequestParam double income) {

    	service.fileIncomeTaxForSalaried(clientId, income);
        return ResponseEntity.ok("Taxation request submitted successfully.");
    }

    @GetMapping("/tax-total")
    public ResponseEntity<Double> getTotalTax(@RequestParam UUID clientId) {
        double total = service.getTotalTaxPaid(clientId);
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public ResponseEntity<Salary> saveSalary(@RequestBody Salary salary) {
        return ResponseEntity.ok(service.saveSalary(salary));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<Salary>> getSalaries(@PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getSalariesByClient(clientId));
    }

    @GetMapping("/{clientId}/summary")
    public ResponseEntity<Map<String, Double>> getSummary(@PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getYearlySummary(clientId));
    }

    @PutMapping("/{salaryId}")
    public ResponseEntity<Salary> updateSalary(@PathVariable UUID salaryId, @RequestBody Salary salary) {
        return service.updateSalary(salaryId, salary)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{salaryId}")
    public ResponseEntity<Void> deleteSalary(@PathVariable UUID salaryId) {
        return service.deleteSalary(salaryId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/employers/{employer}")
    public ResponseEntity<List<Salary>> getByEmployer(@PathVariable String employer) {
        return ResponseEntity.ok(service.getSalariesByEmployer(employer));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Salary>> bulkUpload(@RequestBody List<Salary> salaryList) {
        return ResponseEntity.ok(service.saveBulk(salaryList));
    }

    @GetMapping("/income/{clientId}")
    public ResponseEntity<Map<String, Double>> totalIncome(@PathVariable UUID clientId) {
        double income = service.calculateTotalIncome(clientId);
        return ResponseEntity.ok(Map.of("totalIncome", income));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Salaried Service is up");
    }
}