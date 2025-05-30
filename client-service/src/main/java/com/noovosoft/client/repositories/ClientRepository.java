package com.noovosoft.client.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noovosoft.client.models.Client;
import com.noovosoft.client.models.ClientType;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    List<Client> findByType(ClientType type);
}