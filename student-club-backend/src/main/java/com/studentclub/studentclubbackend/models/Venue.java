package com.studentclub.studentclubbackend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@Table(name = "venues")
@EqualsAndHashCode(exclude = {"events"})
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "venue", fetch = FetchType.LAZY)
    private Set<Event> events;

    @Version
    private long version;
}
