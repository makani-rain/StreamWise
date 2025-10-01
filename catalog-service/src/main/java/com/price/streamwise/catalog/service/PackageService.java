package com.price.streamwise.catalog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.catalog.model.PackageEntity;
import com.price.streamwise.catalog.repository.PackageRepository;

@Service
public class PackageService {
    private final PackageRepository repo;

    public PackageService(PackageRepository repo) {
        this.repo = repo;
    }

    public List<PackageEntity> findAll() {
        return repo.findAll();
    }

    public PackageEntity findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public PackageEntity save(PackageEntity p) {
        return repo.save(p);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
