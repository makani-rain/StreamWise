package com.price.streamwise.consumer.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.consumer.model.PackageEntity;
import com.price.streamwise.consumer.service.PackageService;

import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/packages")
public class PackageController {
    private final PackageService service;

    public PackageController(PackageService service) {
        this.service = service;
    }

    @RolesAllowed({ "ADMIN" })
    @GetMapping("/refresh")
    public List<PackageEntity> refresh() {
        List<PackageEntity> packages = service.refreshPackages();
        return packages;
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping
    public List<PackageEntity> all() {
        return service.findAll();
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/{id}")
    public PackageEntity get(@PathVariable UUID id) {
        return service.findById(id);
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @PostMapping
    public PackageEntity create(@RequestBody PackageEntity p) {
        return service.save(p);
    }

    @RolesAllowed({ "ADMIN" })
    @PutMapping("/{id}")
    public PackageEntity update(@PathVariable UUID id, @RequestBody PackageEntity p) {
        p.setPackageId(id);
        return service.save(p);
    }

    @RolesAllowed({ "ADMIN" })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
