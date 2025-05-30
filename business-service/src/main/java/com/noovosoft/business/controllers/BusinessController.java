package com.noovosoft.business.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noovosoft.business.models.Business;
import com.noovosoft.business.services.BusinessService;

@RestController
@RequestMapping("/businesses")
public class BusinessController {

    @Autowired
    private BusinessService service;
    
    @PostMapping("/tax-file")
    public ResponseEntity<String> fileBusinessTax(
            @RequestParam UUID clientId,
            @RequestParam double revenue,
            @RequestParam String taxSubType) {

    	service.fileBusinessTax(clientId, revenue, taxSubType);
        return ResponseEntity.ok("Business tax submitted successfully.");
    }

    @GetMapping("/tax-total")
    public ResponseEntity<Double> getTotalTax(@RequestParam UUID clientId) {
        double total = service.getTotalTaxPaid(clientId);
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public ResponseEntity<Business> saveBusiness(@RequestBody Business business) {
        return ResponseEntity.ok(service.saveBusiness(business));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<Business>> getBusinesses(@PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getBusinessesByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Business> updateBusiness(@PathVariable UUID id, @RequestBody Business business) {
        return service.updateBusiness(id, business)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable UUID id) {
        return service.deleteBusiness(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/gst/{gstNumber}")
    public ResponseEntity<List<Business>> getByGstNumber(@PathVariable String gstNumber) {
        return ResponseEntity.ok(service.findByGstNumber(gstNumber));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Business>> saveBulk(@RequestBody List<Business> businesses) {
        return ResponseEntity.ok(service.saveBulk(businesses));
    }

    @GetMapping("/turnover/{clientId}")
    public ResponseEntity<Map<String, Double>> getTotalTurnover(@PathVariable UUID clientId) {
        double turnover = service.getTotalTurnover(clientId);
        return ResponseEntity.ok(Map.of("totalTurnover", turnover));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Business Service is up");
    }
}