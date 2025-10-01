package com.price.streamwise.consumer.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.consumer.model.PackageEntity;
import com.price.streamwise.consumer.service.PackageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/packages")
public class PackageController {
    private final PackageService service;

    public PackageController(PackageService service) {
        this.service = service;
    }

    @GetMapping
    public List<PackageEntity> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PackageEntity get(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public PackageEntity create(@RequestBody PackageEntity p) {
        return service.save(p);
    }

    @PutMapping("/{id}")
    public PackageEntity update(@PathVariable UUID id, @RequestBody PackageEntity p) {
        p.setPackageId(id);
        return service.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
