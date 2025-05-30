package com.noovosoft.house.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noovosoft.house.client.Taxation;
import com.noovosoft.house.client.TaxationClient;
import com.noovosoft.house.models.House;
import com.noovosoft.house.repositories.HouseRepository;

@Service
public class HouseService {

    @Autowired
    private HouseRepository repository;
    
    @Autowired
    private TaxationClient taxationClient;

    public void filePropertyTax(UUID clientId, double propertyValue) {
        Taxation tax = new Taxation();
        tax.setClientId(clientId);
        tax.setClientType("House");
        tax.setTaxType("Property Tax");
        tax.setTaxableAmount(propertyValue);
        tax.setTaxAmount(propertyValue * 0.05); // 5% property tax example
        tax.setAssessmentDate(LocalDate.now());
        tax.setStatus("Filed");

        taxationClient.fileTax(tax);
    }

    public double getTotalTaxPaid(UUID clientId) {
        return taxationClient.getTotalTaxPaid(clientId);
    }
    
    public House saveHouse(House house) {
        return repository.save(house);
    }

    public List<House> getHousesByClient(UUID clientId) {
        return repository.findByClientId(clientId);
    }

    public Optional<House> updateHouse(UUID id, House updated) {
        return repository.findById(id).map(existing -> {
            existing.setAddress(updated.getAddress());
            existing.setCity(updated.getCity());
            existing.setMarketValue(updated.getMarketValue());
            existing.setRented(updated.isRented());
            return repository.save(existing);
        });
    }

    public boolean deleteHouse(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<House> findByCity(String city) {
        return repository.findByCity(city);
    }

    public List<House> saveBulk(List<House> houses) {
        return repository.saveAll(houses);
    }

    public double getTotalMarketValue(UUID clientId) {
        return repository.findByClientId(clientId)
                .stream()
                .mapToDouble(House::getMarketValue)
                .sum();
    }
}
