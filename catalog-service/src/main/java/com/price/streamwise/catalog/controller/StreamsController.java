package com.price.streamwise.catalog.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.catalog.model.Streams;
import com.price.streamwise.catalog.model.StreamsId;
import com.price.streamwise.catalog.service.StreamsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/streams")
public class StreamsController {
    private final StreamsService service;

    public StreamsController(StreamsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Streams> all() {
        return service.findAll();
    }

    @GetMapping("/{serviceId}/{titleId}")
    public Streams get(@PathVariable UUID serviceId, @PathVariable UUID titleId) {
        return service.findById(new StreamsId(serviceId, titleId));
    }

    @PostMapping
    public Streams create(@RequestBody Streams s) {
        return service.save(s);
    }

    @PutMapping("/{serviceId}/{titleId}")
    public Streams update(@PathVariable UUID serviceId, @PathVariable UUID titleId, @RequestBody Streams s) {
        s.setId(new StreamsId(serviceId, titleId));
        return service.save(s);
    }

    @DeleteMapping("/{serviceId}/{titleId}")
    public void delete(@PathVariable UUID serviceId, @PathVariable UUID titleId) {
        service.delete(new StreamsId(serviceId, titleId));
    }
}
