package com.noovosoft.house.controllers;

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

import com.noovosoft.house.models.House;
import com.noovosoft.house.services.HouseService;

@RestController
@RequestMapping("/houses")
public class HouseController {

    @Autowired
    private HouseService service;
    
    @PostMapping("/tax-file")
    public ResponseEntity<String> filePropertyTax(
            @RequestParam UUID clientId,
            @RequestParam double propertyValue) {

    	service.filePropertyTax(clientId, propertyValue);
        return ResponseEntity.ok("Property tax submitted successfully.");
    }

    /**
     * Get total property tax paid by house client.
     */
    @GetMapping("/tax-total")
    public ResponseEntity<Double> getTotalTax(@RequestParam UUID clientId) {
        double total = service.getTotalTaxPaid(clientId);
        return ResponseEntity.ok(total);
    }
    
    @PostMapping
    public ResponseEntity<House> saveHouse(@RequestBody House house) {
        return ResponseEntity.ok(service.saveHouse(house));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<House>> getHouses(@PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getHousesByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<House> updateHouse(@PathVariable UUID id, @RequestBody House house) {
        return service.updateHouse(id, house)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable UUID id) {
        return service.deleteHouse(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<House>> getByCity(@PathVariable String city) {
        return ResponseEntity.ok(service.findByCity(city));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<House>> saveBulk(@RequestBody List<House> houses) {
        return ResponseEntity.ok(service.saveBulk(houses));
    }

    @GetMapping("/market-value/{clientId}")
    public ResponseEntity<Map<String, Double>> getTotalMarketValue(@PathVariable UUID clientId) {
        double total = service.getTotalMarketValue(clientId);
        return ResponseEntity.ok(Map.of("totalMarketValue", total));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("House Service is up");
    }
}