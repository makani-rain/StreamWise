package com.price.streamwise.consumer_service.repository;

import com.price.streamwise.consumer_service.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConsumerRepository extends JpaRepository<Consumer, UUID> {
}
