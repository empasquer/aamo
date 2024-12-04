package com.example.aamo.repositories;

import com.example.aamo.models.DELKommune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DELKommuneRepository extends JpaRepository<DELKommune, Integer> {

    // If the query doesnt work because of a bad name do this:
    // @Query("SELECT k FROM Kommune k WHERE k.region.kode = :kode")
    // List<Kommune> findByRegionskode(@Param("kode") String regionskode);
    // needs to be the name of something specific -- thks JPA


    List<DELKommune> findByRegion_Kode(String regionskode);

}
