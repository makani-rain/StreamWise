package com.price.streamwise.catalog.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Embeddable
public class StreamsId implements Serializable {
    @Column(name = "service_id")
    private UUID serviceId;

    @Column(name = "title_id")
    private UUID titleId;

    public StreamsId(UUID serviceId, UUID titleId) {
        this.serviceId = serviceId;
        this.titleId = titleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreamsId streamsId = (StreamsId) o;
        return Objects.equals(serviceId, streamsId.serviceId) && Objects.equals(titleId, streamsId.titleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, titleId);
    }
}
