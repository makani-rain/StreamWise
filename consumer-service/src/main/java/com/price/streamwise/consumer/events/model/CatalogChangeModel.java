package com.price.streamwise.consumer.events.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CatalogChangeModel {
	private String type;
    private String action;
    private String catalogId;
    private String correlationId;

    public CatalogChangeModel(){
        super();
    }

    public  CatalogChangeModel(String type, String action, String catalogId, String correlationId) {
        super();
        this.type   = type;
        this.action = action;
        this.catalogId = catalogId;
        this.correlationId = correlationId;
    }
}
