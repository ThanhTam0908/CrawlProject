package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.project.compareproduct.backend.model.LaptopFPT;

@Repository
public interface LaptopFPTRepository extends JpaRepository<LaptopFPT, Long> {
    LaptopFPT findByName(String name);

    @Query("SELECT p.url FROM LaptopFPT p")
    List<String> findAllLaptopFPTUrl();
}
