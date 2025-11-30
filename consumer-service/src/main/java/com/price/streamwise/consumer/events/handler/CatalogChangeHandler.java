package com.price.streamwise.consumer.events.handler;

import com.price.streamwise.consumer.events.model.CatalogChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class CatalogChangeHandler {

    private static final Logger logger = LoggerFactory.getLogger(CatalogChangeHandler.class);

    @Bean
    public Consumer<CatalogChangeModel> inboundCatalogChanges() {
        return catalog -> {
            logger.debug("Received a message of type " + catalog.getType());
            logger.debug("Received a message with an event {} from the catalog service for the catalog id {} ", 
                    catalog.getType(), catalog.getCatalogId());
        };
    }
}
