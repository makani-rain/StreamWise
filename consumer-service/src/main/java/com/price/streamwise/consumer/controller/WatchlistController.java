package com.price.streamwise.consumer.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.consumer.model.Watchlist;
import com.price.streamwise.consumer.service.WatchlistService;

import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/watchlists")
public class WatchlistController {
    private final WatchlistService service;

    public WatchlistController(WatchlistService service) {
        this.service = service;
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping
    public List<Watchlist> all() {
        return service.findAll();
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/{id}")
    public Watchlist get(@PathVariable UUID id) {
        return service.findById(id);
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @PostMapping
    public Watchlist create(@RequestBody Watchlist w) {
        return service.save(w);
    }

    @RolesAllowed({ "ADMIN" })
    @PutMapping("/{id}")
    public Watchlist update(@PathVariable UUID id, @RequestBody Watchlist w) {
        w.setWatchlistId(id);
        return service.save(w);
    }

    @RolesAllowed({ "ADMIN" })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
