package com.price.streamwise.consumer.model;

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
@Table(name = "watchlist")
public class Watchlist {
    @Id
    @Column(name = "watchlist_id")
    private UUID watchlistId;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    public Watchlist() { this.watchlistId = UUID.randomUUID(); }
}
