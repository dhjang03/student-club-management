package com.studentclub.studentclubbackend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "events")
@EqualsAndHashCode(exclude = {"rsvps"})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer numOfAttendees;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rsvp> rsvps = new HashSet<>();

    @Version
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public boolean updateAttendees(int amount) {
        if (numOfAttendees + amount > capacity) {
            return false;
        }
        numOfAttendees += amount;
        return true;
    }
}
