package com.price.streamwise.catalog.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@Table(name = "streaming_service")
public class StreamingService {
    @Id
    @Column(name = "service_id")
    private UUID serviceId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public StreamingService() {
        this.serviceId = UUID.randomUUID();
    }
}
