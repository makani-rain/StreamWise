package com.price.streamwise.catalog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.catalog.events.source.SimpleSourceBean;
import com.price.streamwise.catalog.model.StreamingService;
import com.price.streamwise.catalog.repository.StreamingServiceRepository;

@Service
public class StreamingServiceService {

    private final SimpleSourceBean simpleSourceBean;
    private final StreamingServiceRepository repo;

    public StreamingServiceService(StreamingServiceRepository repo, SimpleSourceBean simpleSourceBean) {
        this.repo = repo;
        this.simpleSourceBean = simpleSourceBean;
    }

    public List<StreamingService> findAll() {
        return repo.findAll();
    }

    public StreamingService findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public StreamingService save(StreamingService s, boolean isUpdate) {
        simpleSourceBean.publishCatalogChange(
                isUpdate ? com.price.streamwise.catalog.events.model.ActionEnum.UPDATED : com.price.streamwise.catalog.events.model.ActionEnum.CREATED,
                "STREAMING_SERVICE",
                s.getServiceId().toString());
        return repo.save(s);
    }

    public void delete(UUID id) {
        simpleSourceBean.publishCatalogChange(
                com.price.streamwise.catalog.events.model.ActionEnum.DELETED,
                "STREAMING_SERVICE",
                id.toString());
        repo.deleteById(id);
    }
}
