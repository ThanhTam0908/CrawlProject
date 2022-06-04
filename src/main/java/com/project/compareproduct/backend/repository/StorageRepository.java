package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.compareproduct.backend.model.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Storage findByChannels(String channels);
}
