package com.price.streamwise.consumer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.consumer.model.PackageEntity;
import com.price.streamwise.consumer.model.StreamingService;
import com.price.streamwise.consumer.repository.PackageRepository;
import com.price.streamwise.consumer.repository.StreamingServiceRepository;
import com.price.streamwise.consumer.service.client.CatalogRestTemplateClient;

@Service
public class PackageService {
    private final PackageRepository repo;
    private final StreamingServiceRepository serviceRepo;
    private final CatalogRestTemplateClient catalogClient;

    public PackageService(PackageRepository repo, StreamingServiceRepository serviceRepo, CatalogRestTemplateClient catalogClient) {
        this.repo = repo;
        this.serviceRepo = serviceRepo;
        this.catalogClient = catalogClient;
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

    public List<PackageEntity> refreshPackages() {
        // Request packages from catalog service
        List<PackageEntity> packages = catalogClient.fetchPackages();

        // Ensure referenced StreamingService entities exist in local DB before saving packages.
        // The catalog may return packages referring to streaming services that are not present here.
        for (PackageEntity p : packages) {
            if (p.getService() != null && p.getService().getName() != null) {
                String name = p.getService().getName();
                List<StreamingService> existing = serviceRepo.findByName(name);
                if (!existing.isEmpty()) {
                    p.setService(existing.get(0));
                } else {
                    serviceRepo.save(p.getService());
                }
            }
        }

        // Now reset packages in local DB
        repo.deleteAll();
        repo.saveAll(packages);
        return repo.findAll();
    }
}
