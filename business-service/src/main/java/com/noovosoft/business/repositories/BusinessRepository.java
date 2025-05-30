package com.noovosoft.business.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noovosoft.business.models.Business;

public interface BusinessRepository extends JpaRepository<Business, UUID> {
    List<Business> findByClientId(UUID clientId);
    List<Business> findByGstNumber(String gstNumber);
}
