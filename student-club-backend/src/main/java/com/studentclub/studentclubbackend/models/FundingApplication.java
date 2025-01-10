package com.studentclub.studentclubbackend.models;

import com.studentclub.studentclubbackend.constants.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.DRAFT;
}
