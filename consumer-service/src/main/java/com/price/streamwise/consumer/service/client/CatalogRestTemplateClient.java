package com.price.streamwise.consumer.service.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.price.streamwise.consumer.model.PackageEntity;

import brave.ScopedSpan;
import brave.Tracer;

@Component
public class CatalogRestTemplateClient {
    
    @Autowired
    private RestTemplate restTemplate;

	@Autowired
	Tracer tracer;

    public List<PackageEntity> fetchPackages(){
        ScopedSpan span = tracer.startScopedSpan("fetchPackages-CatalogRestTemplateClient");
        ResponseEntity<List<PackageEntity>> restExchange = 
                restTemplate.exchange(
                   "http://gateway:8072/catalog/packages",
                   HttpMethod.GET,
                   null, new ParameterizedTypeReference<List<PackageEntity>>() {});

        return restExchange.getBody();
    }

}
