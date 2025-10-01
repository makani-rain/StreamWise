package com.price.streamwise.catalog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.catalog.model.Title;
import com.price.streamwise.catalog.repository.TitleRepository;

@Service
public class TitleService {
    private final TitleRepository repo;

    public TitleService(TitleRepository repo) {
        this.repo = repo;
    }

    public List<Title> findAll() {
        return repo.findAll();
    }

    public Title findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Title save(Title t) {
        return repo.save(t);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
