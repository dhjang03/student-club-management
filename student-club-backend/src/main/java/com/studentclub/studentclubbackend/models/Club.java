package com.studentclub.studentclubbackend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "clubs")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal funds;

    @OneToMany(mappedBy = "club")
    private Set<Event> events;

    @OneToOne(mappedBy = "club")
    private FundingApplication funding;

    @ManyToMany
    @JoinTable(
        name = "club_members",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> members = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "club_admins",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> admins = new HashSet<>();
}
