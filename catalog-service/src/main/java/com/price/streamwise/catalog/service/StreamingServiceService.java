package com.price.streamwise.catalog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.catalog.model.StreamingService;
import com.price.streamwise.catalog.repository.StreamingServiceRepository;

@Service
public class StreamingServiceService {
    private final StreamingServiceRepository repo;

    public StreamingServiceService(StreamingServiceRepository repo) {
        this.repo = repo;
    }

    public List<StreamingService> findAll() {
        return repo.findAll();
    }

    public StreamingService findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public StreamingService save(StreamingService s) {
        return repo.save(s);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
