package com.price.streamwise.consumer.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Consumer {
    @Id
    private UUID consumerId;

    @Column(nullable=false)
    private String name;

    private String address;
    private Integer budget;

    public Consumer() { this.consumerId = UUID.randomUUID(); }

}
