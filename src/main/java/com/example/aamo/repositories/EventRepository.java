package com.example.aamo.repositories;

import com.example.aamo.models.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

}
