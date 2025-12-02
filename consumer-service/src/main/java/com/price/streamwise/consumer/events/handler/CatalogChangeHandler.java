package com.price.streamwise.consumer.events.handler;

import com.price.streamwise.consumer.events.model.CatalogChangeModel;
import com.price.streamwise.consumer.service.PackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class CatalogChangeHandler {

    private static final Logger logger = LoggerFactory.getLogger(CatalogChangeHandler.class);
    private final PackageService packageService;

    public CatalogChangeHandler(PackageService packageService) {
        this.packageService = packageService;
    }

    @Bean
    public Consumer<CatalogChangeModel> inboundCatalogChanges() {
        return catalog -> {
            logger.info("Received catalog change event: action={}, objectType={}, id={}", 
                    catalog.getAction(), catalog.getObjectType(), catalog.getId());
            
            if (catalog.getObjectType() == "PACKAGE") {
                try {
                    // Refresh packages from catalog service whenever any catalog change occurs
                    packageService.refreshPackages();
                    logger.info("Successfully refreshed packages after catalog change event");
                } catch (Exception e) {
                    logger.error("Error refreshing packages after catalog change event", e);
                }
            }
        };
    }
}
