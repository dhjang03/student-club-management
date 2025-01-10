package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.RSVP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RSVPRepository extends JpaRepository<RSVP, Long> {
}
