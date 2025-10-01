package com.price.streamwise.consumer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.consumer.model.WatchlistItem;
import com.price.streamwise.consumer.repository.WatchlistItemRepository;

@Service
public class WatchlistItemService {
    private final WatchlistItemRepository repo;
    public WatchlistItemService(WatchlistItemRepository repo) { this.repo = repo; }

    public List<WatchlistItem> findAll() { return repo.findAll(); }
    public WatchlistItem findById(UUID id) { return repo.findById(id).orElse(null); }
    public WatchlistItem save(WatchlistItem w) { return repo.save(w); }
    public void delete(UUID id) { repo.deleteById(id); }
}
