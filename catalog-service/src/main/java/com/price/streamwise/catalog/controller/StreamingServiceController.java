package com.price.streamwise.catalog.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.catalog.model.StreamingService;
import com.price.streamwise.catalog.service.StreamingServiceService;

import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/streaming-services")
public class StreamingServiceController {
    private final StreamingServiceService service;

    public StreamingServiceController(StreamingServiceService service) {
        this.service = service;
    }

    @RolesAllowed({ "ADMIN", "USER" })  
    @GetMapping
    public List<StreamingService> all() {
        return service.findAll();
    }

    @RolesAllowed({ "ADMIN", "USER" })  
    @GetMapping("/{id}")
    public StreamingService get(@PathVariable UUID id) {
        return service.findById(id);
    }

    @RolesAllowed({ "ADMIN" })  
    @PostMapping
    public StreamingService create(@RequestBody StreamingService s) {
        return service.save(s);
    }

    @RolesAllowed({ "ADMIN" })  
    @PutMapping("/{id}")
    public StreamingService update(@PathVariable UUID id, @RequestBody StreamingService s) {
        s.setServiceId(id);
        return service.save(s);
    }

    @RolesAllowed({ "ADMIN" })  
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
