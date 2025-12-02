package com.price.streamwise.catalog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.catalog.events.source.SimpleSourceBean;
import com.price.streamwise.catalog.model.Title;
import com.price.streamwise.catalog.repository.TitleRepository;

@Service
public class TitleService {
    private final TitleRepository repo;
    private final SimpleSourceBean simpleSourceBean;

    public TitleService(TitleRepository repo, SimpleSourceBean simpleSourceBean) {
        this.repo = repo;
        this.simpleSourceBean = simpleSourceBean;
    }

    public List<Title> findAll() {
        return repo.findAll();
    }

    public Title findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Title save(Title t, boolean isUpdate) {
        simpleSourceBean.publishCatalogChange(
                isUpdate ? com.price.streamwise.catalog.events.model.ActionEnum.UPDATED : com.price.streamwise.catalog.events.model.ActionEnum.CREATED,
                "TITLE",
                t.getTitleId().toString());
        return repo.save(t);
    }

    public void delete(UUID id) {
        simpleSourceBean.publishCatalogChange(
                com.price.streamwise.catalog.events.model.ActionEnum.DELETED,
                "TITLE",
                id.toString());
        repo.deleteById(id);
    }
}
