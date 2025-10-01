package com.price.streamwise.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.price.streamwise.consumer.model.StreamingService;

import java.util.UUID;

@Repository
public interface StreamingServiceRepository extends JpaRepository<StreamingService, UUID> {}
