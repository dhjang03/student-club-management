package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
