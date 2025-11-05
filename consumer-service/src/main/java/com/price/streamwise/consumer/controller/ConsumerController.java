package com.price.streamwise.consumer.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.consumer.model.Consumer;
import com.price.streamwise.consumer.service.ConsumerService;

import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {
    private final ConsumerService service;
    public ConsumerController(ConsumerService service) { this.service = service; }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping
    public List<Consumer> all() { return service.findAll(); }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/{id}")
    public Consumer get(@PathVariable UUID id) { return service.findById(id); }

    @RolesAllowed({ "ADMIN", "USER" })
    @PostMapping
    public Consumer create(@RequestBody Consumer c) { return service.save(c); }

    @RolesAllowed({ "ADMIN" })
    @PutMapping("/{id}")
    public Consumer update(@PathVariable UUID id, @RequestBody Consumer c) {
        c.setConsumerId(id);
        return service.save(c);
    }

    @RolesAllowed({ "ADMIN" })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.delete(id); }
}
