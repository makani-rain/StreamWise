package com.price.streamwise.consumer.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.consumer.model.WatchlistItem;
import com.price.streamwise.consumer.service.WatchlistItemService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/watchlist-items")
public class WatchlistItemController {
    private final WatchlistItemService service;

    public WatchlistItemController(WatchlistItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<WatchlistItem> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public WatchlistItem get(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public WatchlistItem create(@RequestBody WatchlistItem w) {
        return service.save(w);
    }

    @PutMapping("/{id}")
    public WatchlistItem update(@PathVariable UUID id, @RequestBody WatchlistItem w) {
        w.setWatchlistItemId(id);
        return service.save(w);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
