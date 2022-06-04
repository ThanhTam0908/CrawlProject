package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.project.compareproduct.backend.model.LaptopDMX;

@Repository
public interface LaptopDMXRepository extends JpaRepository<LaptopDMX, Long> {
    LaptopDMX findByName(String name);

    @Query("SELECT p.url FROM LaptopDMX p")
    List<String> findAllLaptopDMXUrl();
}
