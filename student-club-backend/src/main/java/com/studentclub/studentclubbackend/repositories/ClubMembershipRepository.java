package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.constants.MembershipRole;
import com.studentclub.studentclubbackend.models.ClubMembership;
import com.studentclub.studentclubbackend.models.embeddables.ClubMembershipId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMembershipRepository extends JpaRepository<ClubMembership, ClubMembershipId> {

    List<ClubMembership> findByClubId(Long clubId);

    List<ClubMembership> findByUserId(Long userId);

    Optional<ClubMembership> findByClubIdAndUserId(Long clubId, Long userId);

    List<ClubMembership> findByClub_IdAndMembershipRole(Long clubId, MembershipRole role);
}
