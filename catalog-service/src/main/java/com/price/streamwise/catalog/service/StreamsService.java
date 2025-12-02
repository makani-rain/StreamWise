package com.price.streamwise.catalog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.price.streamwise.catalog.events.source.SimpleSourceBean;
import com.price.streamwise.catalog.model.Streams;
import com.price.streamwise.catalog.model.StreamsId;
import com.price.streamwise.catalog.repository.StreamsRepository;

@Service
public class StreamsService {
    private final SimpleSourceBean simpleSourceBean;
    private final StreamsRepository repo;

    public StreamsService(StreamsRepository repo, SimpleSourceBean simpleSourceBean) {
        this.repo = repo;
        this.simpleSourceBean = simpleSourceBean;
    }

    public List<Streams> findAll() {
        return repo.findAll();
    }

    public Streams findById(StreamsId id) {
        return repo.findById(id).orElse(null);
    }

    public Streams save(Streams s, boolean isUpdate) {
        simpleSourceBean.publishCatalogChange(
                isUpdate ? com.price.streamwise.catalog.events.model.ActionEnum.UPDATED : com.price.streamwise.catalog.events.model.ActionEnum.CREATED,
                "STREAMS",
                s.getId().toString());
        return repo.save(s);
    }

    public void delete(StreamsId id) {
        simpleSourceBean.publishCatalogChange(
                com.price.streamwise.catalog.events.model.ActionEnum.DELETED,
                "STREAMS",
                id.toString());
        repo.deleteById(id);
    }
}
