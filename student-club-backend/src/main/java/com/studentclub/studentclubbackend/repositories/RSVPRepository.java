package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.Rsvp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RSVPRepository extends JpaRepository<Rsvp, Long> {
    List<Rsvp> findByResponderId(Long responderId);
}
