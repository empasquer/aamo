package com.example.aamo.repositories;

import com.example.aamo.models.DELRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DELRegionRepository extends JpaRepository<DELRegion, Integer> {

}
