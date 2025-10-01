package com.price.streamwise.consumer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.consumer.model.Watchlist;
import com.price.streamwise.consumer.repository.WatchlistRepository;

@Service
public class WatchlistService {
    private final WatchlistRepository repo;
    public WatchlistService(WatchlistRepository repo) { this.repo = repo; }

    public List<Watchlist> findAll() { return repo.findAll(); }
    public Watchlist findById(UUID id) { return repo.findById(id).orElse(null); }
    public Watchlist save(Watchlist w) { return repo.save(w); }
    public void delete(UUID id) { repo.deleteById(id); }
}
