package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.compareproduct.backend.model.Screen;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    Screen findByPpi(long ppi);
}