package com.studentclub.studentclubbackend.models;

import com.studentclub.studentclubbackend.constants.MembershipRole;
import com.studentclub.studentclubbackend.models.embeddables.ClubMembershipId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "club_memberships")
public class ClubMembership {

    @EmbeddedId
    private ClubMembershipId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clubId")
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipRole membershipRole = MembershipRole.MEMBER;

    @Column
    private LocalDate dateJoined = LocalDate.now();
}
