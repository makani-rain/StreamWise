package com.price.streamwise.catalog.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.catalog.model.PackageEntity;
import com.price.streamwise.catalog.service.PackageService;

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

    @RolesAllowed({ "ADMIN" })  
    @PostMapping
    public PackageEntity create(@RequestBody PackageEntity p) {
        return service.save(p, false);
    }

    @RolesAllowed({ "ADMIN" })  
    @PutMapping("/{id}")
    public PackageEntity update(@PathVariable UUID id, @RequestBody PackageEntity p) {
        p.setPackageId(id);
        
        return service.save(p, true);
    }

    @RolesAllowed({ "ADMIN" })  
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
