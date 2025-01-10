package com.studentclub.studentclubbackend.models;

import com.studentclub.studentclubbackend.constants.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "fundings")
public class FundingApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "club_id", nullable = false, unique = true)
    private Club club;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.DRAFT;
}
