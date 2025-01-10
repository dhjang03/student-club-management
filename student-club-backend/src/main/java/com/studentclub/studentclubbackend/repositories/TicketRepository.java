package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
