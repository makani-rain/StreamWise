package com.price.streamwise.catalog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.price.streamwise.catalog.events.source.SimpleSourceBean;
import com.price.streamwise.catalog.model.PackageEntity;
import com.price.streamwise.catalog.repository.PackageRepository;

import brave.ScopedSpan;
import brave.Tracer;

@Service
public class PackageService {

    private final SimpleSourceBean simpleSourceBean;
    private final PackageRepository repo;
    private final Tracer tracer;

    public PackageService(PackageRepository repo, SimpleSourceBean simpleSourceBean, Tracer tracer) {
        this.repo = repo;
        this.simpleSourceBean = simpleSourceBean;
        this.tracer = tracer;
    }

    public List<PackageEntity> findAll() {
        ScopedSpan span = tracer.startScopedSpan("findAll-PackageService");
        try {
            return repo.findAll();
        } finally {
            span.finish();
        }
    }

    public PackageEntity findById(UUID id) {
        ScopedSpan span = tracer.startScopedSpan("findById-PackageService");
        try {
            return repo.findById(id).orElse(null);
        } finally {
            span.finish();
        }
    }

    public PackageEntity save(PackageEntity p, boolean isUpdate) {
        ScopedSpan span = tracer.startScopedSpan("save-PackageService");
        try {
            simpleSourceBean.publishCatalogChange(
                    isUpdate ? com.price.streamwise.catalog.events.model.ActionEnum.UPDATED : com.price.streamwise.catalog.events.model.ActionEnum.CREATED,
                    "PACKAGE",
                    p.getPackageId().toString());
            return repo.save(p);
        } finally {
            span.finish();
        }
    }

    public void delete(UUID id) {
        ScopedSpan span = tracer.startScopedSpan("delete-PackageService");
        try {
            simpleSourceBean.publishCatalogChange(
                    com.price.streamwise.catalog.events.model.ActionEnum.DELETED,
                    "PACKAGE",
                    id.toString());
            repo.deleteById(id);
        } finally {
            span.finish();
        }
    }
}
