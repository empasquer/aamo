package com.example.aamo.repositories;

import com.example.aamo.models.ArtWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkRepository extends JpaRepository<ArtWork, Integer> {
    List<ArtWork> findByTypeFalse();

    List<ArtWork> findByTypeTrue();

    List<ArtWork> findByTypeTrue(Sort sort);

    Page<ArtWork> findByTypeTrue(Pageable pageable);



}
