package com.project.compareproduct.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.compareproduct.backend.model.GraphicCard;

@Repository
public interface GraphicCardRepository extends JpaRepository<GraphicCard, Long> {

    GraphicCard findByName(String name);

}
