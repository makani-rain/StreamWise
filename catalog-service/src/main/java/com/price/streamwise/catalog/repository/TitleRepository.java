package com.price.streamwise.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.price.streamwise.catalog.model.Title;

import java.util.UUID;

public interface TitleRepository extends JpaRepository<Title, UUID> {
}
