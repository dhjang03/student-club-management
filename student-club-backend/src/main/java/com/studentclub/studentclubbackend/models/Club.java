package com.studentclub.studentclubbackend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private BigDecimal funds;

    @OneToMany(mappedBy = "club")
    private Set<Event> events;

    @OneToOne(mappedBy = "club")
    private FundingApplication funding;
}
