package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.compareproduct.backend.model.Battery;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    Battery findByCapacity(double capacity);

}
