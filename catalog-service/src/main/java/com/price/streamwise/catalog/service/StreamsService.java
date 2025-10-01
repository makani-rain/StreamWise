package com.price.streamwise.catalog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.price.streamwise.catalog.model.Streams;
import com.price.streamwise.catalog.model.StreamsId;
import com.price.streamwise.catalog.repository.StreamsRepository;

@Service
public class StreamsService {
    private final StreamsRepository repo;

    public StreamsService(StreamsRepository repo) {
        this.repo = repo;
    }

    public List<Streams> findAll() {
        return repo.findAll();
    }

    public Streams findById(StreamsId id) {
        return repo.findById(id).orElse(null);
    }

    public Streams save(Streams s) {
        return repo.save(s);
    }

    public void delete(StreamsId id) {
        repo.deleteById(id);
    }
}
