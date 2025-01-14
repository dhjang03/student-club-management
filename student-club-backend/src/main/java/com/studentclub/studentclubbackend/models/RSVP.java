package com.studentclub.studentclubbackend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "rsvps")
public class Rsvp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responder_id", nullable = false)
    private User responder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToMany(mappedBy = "rsvp", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Version
    private long version;

    public boolean isOwnedBy(Long userId) {
        return responder.getId() == userId;
    }
}
