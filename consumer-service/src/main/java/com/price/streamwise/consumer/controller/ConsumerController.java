package com.price.streamwise.consumer.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.consumer.model.Consumer;
import com.price.streamwise.consumer.repository.ConsumerRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {
    private final ConsumerRepository repo;
    public ConsumerController(ConsumerRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Consumer> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Consumer get(@PathVariable UUID id) { return repo.findById(id).orElse(null); }

    @PostMapping
    public Consumer create(@RequestBody Consumer c) { return repo.save(c); }

    @PutMapping("/{id}")
    public Consumer update(@PathVariable UUID id, @RequestBody Consumer c) {
        c.setConsumerId(id);
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { repo.deleteById(id); }
}
