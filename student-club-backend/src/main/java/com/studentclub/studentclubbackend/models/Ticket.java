package com.studentclub.studentclubbackend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "rsvp_id", nullable = false)
    private RSVP rsvp;

    @ManyToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private User attendee;
}
