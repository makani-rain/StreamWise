package com.price.streamwise.consumer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.consumer.model.Consumer;
import com.price.streamwise.consumer.repository.ConsumerRepository;

@Service
public class ConsumerService {
    private final ConsumerRepository repo;
    public ConsumerService(ConsumerRepository repo) { this.repo = repo; }

    public List<Consumer> findAll() { return repo.findAll(); }
    public Consumer findById(UUID id) { return repo.findById(id).orElse(null); }
    public Consumer save(Consumer c) { return repo.save(c); }
    public void delete(UUID id) { repo.deleteById(id); }
}
