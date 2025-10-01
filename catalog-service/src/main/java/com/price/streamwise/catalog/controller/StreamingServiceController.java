package com.price.streamwise.catalog.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.catalog.model.StreamingService;
import com.price.streamwise.catalog.service.StreamingServiceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/streaming-services")
public class StreamingServiceController {
    private final StreamingServiceService service;

    public StreamingServiceController(StreamingServiceService service) {
        this.service = service;
    }

    @GetMapping
    public List<StreamingService> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public StreamingService get(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public StreamingService create(@RequestBody StreamingService s) {
        return service.save(s);
    }

    @PutMapping("/{id}")
    public StreamingService update(@PathVariable UUID id, @RequestBody StreamingService s) {
        s.setServiceId(id);
        return service.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
