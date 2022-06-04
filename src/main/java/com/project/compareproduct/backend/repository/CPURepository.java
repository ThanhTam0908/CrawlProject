package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.compareproduct.backend.model.CPU;

@Repository
public interface CPURepository extends JpaRepository<CPU, Long> {
    CPU findByName(String name);
}
