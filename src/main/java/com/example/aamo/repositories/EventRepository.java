package com.example.aamo.repositories;

import com.example.aamo.models.Event;
import com.example.aamo.models.EventDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByOrderByStartDateDesc();

    // Custom JPQL query with projection into the DTO
    @Query("SELECT new com.example.aamo.models.EventDTO(e.eventId, e.title, e.startDate) FROM Event e ORDER BY e.startDate DESC")
    List<EventDTO> findEventSummary();

}

