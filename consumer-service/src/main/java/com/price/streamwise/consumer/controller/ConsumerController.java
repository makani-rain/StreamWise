package com.price.streamwise.consumer.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.consumer.model.Consumer;
import com.price.streamwise.consumer.service.ConsumerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {
    private final ConsumerService service;
    public ConsumerController(ConsumerService service) { this.service = service; }

    @GetMapping
    public List<Consumer> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public Consumer get(@PathVariable UUID id) { return service.findById(id); }

    @PostMapping
    public Consumer create(@RequestBody Consumer c) { return service.save(c); }

    @PutMapping("/{id}")
    public Consumer update(@PathVariable UUID id, @RequestBody Consumer c) {
        c.setConsumerId(id);
        return service.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.delete(id); }
}
