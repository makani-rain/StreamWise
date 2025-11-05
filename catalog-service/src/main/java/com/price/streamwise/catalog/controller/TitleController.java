package com.price.streamwise.catalog.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.catalog.model.Title;
import com.price.streamwise.catalog.service.TitleService;

import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/titles")
public class TitleController {
    private final TitleService service;

    public TitleController(TitleService service) {
        this.service = service;
    }

    @RolesAllowed({ "ADMIN", "USER" })  
    @GetMapping
    public List<Title> all() {
        return service.findAll();
    }

    @RolesAllowed({ "ADMIN", "USER" })  
    @GetMapping("/{id}")
    public Title get(@PathVariable UUID id) {
        return service.findById(id);
    }

    @RolesAllowed({ "ADMIN" })
    @PostMapping
    public Title create(@RequestBody Title t) {
        return service.save(t);
    }

    @RolesAllowed({ "ADMIN" })
    @PutMapping("/{id}")
    public Title update(@PathVariable UUID id, @RequestBody Title t) {
        t.setTitleId(id);
        return service.save(t);
    }

    @RolesAllowed({ "ADMIN" })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
