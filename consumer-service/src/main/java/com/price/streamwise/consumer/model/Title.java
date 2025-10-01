package com.price.streamwise.consumer.model;

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
@Table(name = "title")
public class Title {
    @Id
    @Column(name = "title_id")
    private UUID titleId;

    @Column(name = "title_name", nullable = false)
    private String titleName;

    private String type;

    public Title() { this.titleId = UUID.randomUUID(); }
}
