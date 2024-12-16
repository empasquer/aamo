package com.example.aamo.repositories;

import com.example.aamo.models.ArtWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkRepository extends JpaRepository<ArtWork, Integer> {
    List<ArtWork> findByTypeFalse();

    List<ArtWork> findByTypeTrue();

}
