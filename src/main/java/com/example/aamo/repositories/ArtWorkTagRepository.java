package com.example.aamo.repositories;

import com.example.aamo.models.ArtWorkTag;
import com.example.aamo.models.TagType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtWorkTagRepository extends JpaRepository<ArtWorkTag, Integer> {
    Optional<ArtWorkTag> findByTagTypeAndTagValue(TagType tagType, String tagValue);
}
