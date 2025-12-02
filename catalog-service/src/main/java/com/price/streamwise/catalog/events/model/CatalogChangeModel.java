package com.price.streamwise.catalog.events.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CatalogChangeModel {
	private String type;
	private String action;
	private String objectType;
	private String id;
	private String correlationId;

	public CatalogChangeModel(String type, String action, String objectType, String id, String correlationId) {
		super();
		this.type = type;
		this.action = action;
		this.objectType = objectType;
		this.id = id;
		this.correlationId = correlationId;
	}
}
