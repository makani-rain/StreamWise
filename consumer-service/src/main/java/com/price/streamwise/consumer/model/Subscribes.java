package com.price.streamwise.consumer.model;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@Table(name = "subscribes")
public class Subscribes {
    @EmbeddedId
    private SubscribesId id;

    private LocalDate renewal_date;

    public Subscribes() {}
}
