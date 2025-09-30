package com.price.streamwise.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.price.streamwise.catalog.model.Title;

import java.util.UUID;

@Repository
public interface TitleRepository extends JpaRepository<Title, UUID> {
}
