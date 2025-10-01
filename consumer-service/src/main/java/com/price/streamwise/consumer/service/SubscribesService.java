package com.price.streamwise.consumer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.price.streamwise.consumer.model.Subscribes;
import com.price.streamwise.consumer.model.SubscribesId;
import com.price.streamwise.consumer.repository.SubscribesRepository;

@Service
public class SubscribesService {
    private final SubscribesRepository repo;
    public SubscribesService(SubscribesRepository repo) { this.repo = repo; }

    public List<Subscribes> findAll() { return repo.findAll(); }
    public Subscribes findById(SubscribesId id) { return repo.findById(id).orElse(null); }
    public Subscribes save(Subscribes s) { return repo.save(s); }
    public void delete(SubscribesId id) { repo.deleteById(id); }
}
