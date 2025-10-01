package com.price.streamwise.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.price.streamwise.consumer.model.Subscribes;
import com.price.streamwise.consumer.model.SubscribesId;

@Repository
public interface SubscribesRepository extends JpaRepository<Subscribes, SubscribesId> {}
