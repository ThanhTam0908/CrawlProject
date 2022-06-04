package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.project.compareproduct.backend.model.LaptopAmz;

@Repository
public interface LaptopAmzRepository extends JpaRepository<LaptopAmz, Long> {
    LaptopAmz findByName(String name);

    @Query(value = "SELECT p.url FROM LaptopAmz p")
    List<String> findAllLaptopAmzUrl();
}