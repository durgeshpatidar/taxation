package com.noovosoft.taxation.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noovosoft.taxation.models.Taxation;
import com.noovosoft.taxation.services.TaxationService;

@RestController
@RequestMapping("/taxations")
public class TaxationController {

    @Autowired
    private TaxationService service;

    @PostMapping
    public ResponseEntity<Taxation> saveTaxation(@RequestBody Taxation taxation) {
        return ResponseEntity.ok(service.saveTaxation(taxation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Taxation> updateTaxation(@PathVariable UUID id, @RequestBody Taxation taxation) {
        return service.updateTaxation(id, taxation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxation(@PathVariable UUID id) {
        return service.deleteTaxation(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Taxation>> getByClientId(@PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getByClientId(clientId));
    }

    @GetMapping("/type/{clientType}")
    public ResponseEntity<List<Taxation>> getByClientType(@PathVariable String clientType) {
        return ResponseEntity.ok(service.getByClientType(clientType));
    }

    @GetMapping("/tax/{taxType}")
    public ResponseEntity<List<Taxation>> getByTaxType(@PathVariable String taxType) {
        return ResponseEntity.ok(service.getByTaxType(taxType));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Taxation>> saveBulk(@RequestBody List<Taxation> taxations) {
        return ResponseEntity.ok(service.saveBulk(taxations));
    }

    @GetMapping("/total/{clientId}")
    public ResponseEntity<Map<String, Double>> getTotalTax(@PathVariable UUID clientId) {
        double total = service.getTotalTaxPaid(clientId);
        return ResponseEntity.ok(Map.of("totalTaxPaid", total));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Taxation Service is up");
    }
}
