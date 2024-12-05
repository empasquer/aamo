package com.example.aamo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.attribute.standard.MediaSize;


public interface MediaRepository extends JpaRepository<MediaSize, Integer> {
}
