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
@Table(name = "watchlist_item")
public class WatchlistItem {
    @Id
    @Column(name = "watchlist_item_id")
    private UUID watchlistItemId;

    @ManyToOne
    @JoinColumn(name = "watchlist_id")
    private Watchlist watchlist;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    public WatchlistItem() { this.watchlistItemId = UUID.randomUUID(); }
}
