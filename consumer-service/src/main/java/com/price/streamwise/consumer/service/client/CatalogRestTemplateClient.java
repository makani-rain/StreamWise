package com.price.streamwise.consumer.service.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.price.streamwise.consumer.model.PackageEntity;

@Component
public class CatalogRestTemplateClient {
    
    @Autowired
    private RestTemplate restTemplate;

    public List<PackageEntity> fetchPackages(){
        ResponseEntity<List<PackageEntity>> restExchange = 
                restTemplate.exchange(
                   "http://gateway:8072/catalog/packages",
                   HttpMethod.GET,
                   null, new ParameterizedTypeReference<List<PackageEntity>>() {});

        return restExchange.getBody();
    }

}
