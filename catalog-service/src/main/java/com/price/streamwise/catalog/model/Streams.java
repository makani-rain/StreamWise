package com.price.streamwise.catalog.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@Table(name = "streams")
public class Streams {
    @EmbeddedId
    private StreamsId id;

    private Long arrival_date;
    private Long leaving_date;

    public Streams() { }

    public Streams(StreamsId id, Long arrival_date, Long leaving_date) {
        this.id = id;
        this.arrival_date = arrival_date;
        this.leaving_date = leaving_date;
    }
}
