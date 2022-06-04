package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.compareproduct.backend.model.Ram;

@Repository
public interface RamRepository extends JpaRepository<Ram, Long> {
    Ram findByChannels(String channels);
}
