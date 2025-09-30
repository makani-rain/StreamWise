package com.price.streamwise.catalog.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.catalog.model.Title;
import com.price.streamwise.catalog.repository.TitleRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/titles")
public class TitleController {
    private final TitleRepository repo;
    public TitleController(TitleRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Title> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Title get(@PathVariable UUID id) { return repo.findById(id).orElse(null); }

    @PostMapping
    public Title create(@RequestBody Title t) { return repo.save(t); }

    @PutMapping("/{id}")
    public Title update(@PathVariable UUID id, @RequestBody Title t) {
        t.setTitleId(id);
        return repo.save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { repo.deleteById(id); }
}
