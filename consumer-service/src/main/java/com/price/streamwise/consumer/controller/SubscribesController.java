package com.price.streamwise.consumer.controller;

import org.springframework.web.bind.annotation.*;

import com.price.streamwise.consumer.model.Subscribes;
import com.price.streamwise.consumer.model.SubscribesId;
import com.price.streamwise.consumer.service.SubscribesService;

import java.util.List;

@RestController
@RequestMapping("/subscribes")
public class SubscribesController {
    private final SubscribesService service;

    public SubscribesController(SubscribesService service) {
        this.service = service;
    }

    @GetMapping
    public List<Subscribes> all() {
        return service.findAll();
    }

    @GetMapping("/{consumerId}/{packageId}")
    public Subscribes get(@PathVariable("consumerId") String consumerId, @PathVariable("packageId") String packageId) {
        SubscribesId id = new SubscribesId();
        id.setConsumerId(java.util.UUID.fromString(consumerId));
        id.setPackageId(java.util.UUID.fromString(packageId));
        return service.findById(id);
    }

    @PostMapping
    public Subscribes create(@RequestBody Subscribes s) {
        return service.save(s);
    }

    @DeleteMapping("/{consumerId}/{packageId}")
    public void delete(@PathVariable("consumerId") String consumerId, @PathVariable("packageId") String packageId) {
        SubscribesId id = new SubscribesId();
        id.setConsumerId(java.util.UUID.fromString(consumerId));
        id.setPackageId(java.util.UUID.fromString(packageId));
        service.delete(id);
    }
}
