package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.constants.MembershipRole;
import com.studentclub.studentclubbackend.models.ClubMembership;

import java.util.List;

public interface MembershipService {

    ClubMembership addMembership(Long clubId, Long userId, MembershipRole role);

    void removeMembership(Long clubId, Long userId);

    void promoteMember(Long clubId, Long userId);

    void demoteAdmin(Long clubId, Long userId);

    boolean isMember(Long clubId, Long userId);

    boolean isAdmin(Long clubId, Long userId);

    List<ClubMembership> getAllMembershipsForClub(Long clubId);

    List<ClubMembership> getAllMembershipsForUser(Long userId);
}
