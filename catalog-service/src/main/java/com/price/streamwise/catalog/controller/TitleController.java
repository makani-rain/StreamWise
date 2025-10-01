package com.price.streamwise.catalog.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.catalog.model.Title;
import com.price.streamwise.catalog.service.TitleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/titles")
public class TitleController {
    private final TitleService service;

    public TitleController(TitleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Title> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Title get(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public Title create(@RequestBody Title t) {
        return service.save(t);
    }

    @PutMapping("/{id}")
    public Title update(@PathVariable UUID id, @RequestBody Title t) {
        t.setTitleId(id);
        return service.save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
