package com.price.streamwise.catalog.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@Entity
public class Title {
    @Id
    private UUID titleId;

    @Column(nullable=false)
    private String titleName;

    private String type;

    public Title() {
        this.titleId = UUID.randomUUID();
    }
}
