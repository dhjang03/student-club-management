package com.studentclub.studentclubbackend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "clubs")
@EqualsAndHashCode(exclude = {"events", "funding", "members", "admins"})
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

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events;

    @OneToOne(mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private FundingApplication funding;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "club_members",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> members = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "club_admins",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> admins = new HashSet<>();

    public void addFunds(BigDecimal amount) {
        this.funds = funds.add(amount);
    }

    public void removeFunds(BigDecimal amount) {
        this.funds = funds.subtract(amount);
    }
}
