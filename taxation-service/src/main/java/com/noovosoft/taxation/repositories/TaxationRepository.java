package com.noovosoft.taxation.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noovosoft.taxation.models.Taxation;

public interface TaxationRepository extends JpaRepository<Taxation, UUID> {
    List<Taxation> findByClientId(UUID clientId);
    List<Taxation> findByClientType(String clientType);
    List<Taxation> findByTaxType(String taxType);
}