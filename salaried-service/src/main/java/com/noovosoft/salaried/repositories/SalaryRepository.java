package com.noovosoft.salaried.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noovosoft.salaried.models.Salary;

public interface SalaryRepository extends JpaRepository<Salary, UUID> {
    List<Salary> findByClientId(UUID clientId);
    List<Salary> findByEmployer(String employer);
}