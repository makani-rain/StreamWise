package com.price.streamwise.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.price.streamwise.catalog.model.Streams;
import com.price.streamwise.catalog.model.StreamsId;

@Repository
public interface StreamsRepository extends JpaRepository<Streams, StreamsId> {
}
