package com.noovosoft.house.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noovosoft.house.models.House;

@Repository
public interface HouseRepository extends JpaRepository<House, UUID> {
    List<House> findByClientId(UUID clientId);
    List<House> findByCity(String city);
}