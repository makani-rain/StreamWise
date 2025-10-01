package com.price.streamwise.consumer.model;

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
public class SubscribesId implements Serializable {
    @Column(name = "consumer_id")
    private UUID consumerId;

    @Column(name = "package_id")
    private UUID packageId;

    public SubscribesId(UUID consumerId, UUID packageId) { this.consumerId = consumerId; this.packageId = packageId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribesId that = (SubscribesId) o;
        return Objects.equals(consumerId, that.consumerId) && Objects.equals(packageId, that.packageId);
    }

    @Override
    public int hashCode() { return Objects.hash(consumerId, packageId); }
}
