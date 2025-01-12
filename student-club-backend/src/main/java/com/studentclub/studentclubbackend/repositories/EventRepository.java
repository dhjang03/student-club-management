package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTitleContainingIgnoreCase(String title);
    List<Event> findByClubId(Long clubId);
}
