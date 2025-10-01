package com.price.streamwise.consumer.model;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@Table(name = "package")
public class PackageEntity {
    @Id
    @Column(name = "package_id")
    private UUID packageId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private StreamingService service;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "period")
    private Integer period;

    @Column(name = "ad_supported")
    private Boolean adSupported;

    @Column(name = "deprecated")
    private Boolean deprecated;

    public PackageEntity() { this.packageId = UUID.randomUUID(); }
}
