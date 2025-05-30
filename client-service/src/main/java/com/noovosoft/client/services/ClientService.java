package com.noovosoft.client.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.noovosoft.client.models.Client;
import com.noovosoft.client.models.ClientType;
import com.noovosoft.client.repositories.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Client createClient(Client client) {
        return repository.save(client);
    }

    public Optional<Client> getClient(UUID id) {
        return repository.findById(id);
    }

    public List<Client> getClients(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public List<Client> getClientsByType(ClientType type) {
        return repository.findByType(type);
    }

    public Optional<Client> updateClient(UUID id, Client updatedClient) {
        return repository.findById(id).map(existing -> {
            existing.setName(updatedClient.getName());
            existing.setType(updatedClient.getType());
            return repository.save(existing);
        });
    }

    public boolean deleteClient(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<String> getClientStatus(UUID id) {
        return repository.findById(id).map(client -> client.isActive() ? "ACTIVE" : "INACTIVE");
    }

    public Optional<Client> updateClientType(UUID id, ClientType newType) {
        return repository.findById(id).map(client -> {
            client.setType(newType);
            return repository.save(client);
        });
    }
}