package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.constants.MembershipRole;
import com.studentclub.studentclubbackend.models.Club;
import com.studentclub.studentclubbackend.models.ClubMembership;
import com.studentclub.studentclubbackend.models.User;
import com.studentclub.studentclubbackend.models.embeddables.ClubMembershipId;
import com.studentclub.studentclubbackend.repositories.ClubMembershipRepository;
import com.studentclub.studentclubbackend.repositories.ClubRepository;
import com.studentclub.studentclubbackend.repositories.UserRepository;
import com.studentclub.studentclubbackend.services.MembershipService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final ClubMembershipRepository membershipRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Override
    public ClubMembership addMembership(Long clubId, Long userId, MembershipRole role) {
        Club club = findClubByIdOrThrow(clubId);
        User user = findUserByIdOrThrow(userId);

        if (isMember(clubId, userId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already a member/admin of this club");
        }

        ClubMembership membership = buildMembership(club, user, role);
        return membershipRepository.save(membership);
    }

    @Override
    public void removeMembership(Long clubId, Long userId) {
        ClubMembership membership = findMembershipOrThrow(clubId, userId);
        membershipRepository.delete(membership);
    }

    @Override
    public void promoteMember(Long clubId, Long userId) {
        updateMembershipRole(clubId, userId, MembershipRole.ADMIN);
    }

    @Override
    public void demoteAdmin(Long clubId, Long userId) {
        updateMembershipRole(clubId, userId, MembershipRole.MEMBER);
    }

    @Override
    public boolean isMember(Long clubId, Long userId) {
        return membershipRepository.findByClubIdAndUserId(clubId, userId).isPresent();
    }

    @Override
    public boolean isAdmin(Long clubId, Long userId) {
        return membershipRepository.findByClubIdAndUserId(clubId, userId)
                .map(membership -> membership.getMembershipRole() == MembershipRole.ADMIN)
                .orElse(false);
    }

    @Override
    public List<ClubMembership> getAllMembershipsForClub(Long clubId) {
        return membershipRepository.findByClubId(clubId);
    }

    @Override
    public List<ClubMembership> getAllMembershipsForUser(Long userId) {
        return membershipRepository.findByUserId(userId);
    }

    private Club findClubByIdOrThrow(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
    }

    private User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private ClubMembership findMembershipOrThrow(Long clubId, Long userId) {
        return membershipRepository.findByClubIdAndUserId(clubId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membership not found"));
    }

    private ClubMembership buildMembership(Club club, User user, MembershipRole role) {
        ClubMembership membership = new ClubMembership();
        membership.setId(new ClubMembershipId(club.getId(), user.getId()));
        membership.setClub(club);
        membership.setUser(user);
        membership.setMembershipRole(role);
        return membership;
    }

    private void updateMembershipRole(Long clubId, Long userId, MembershipRole newRole) {
        ClubMembership membership = findMembershipOrThrow(clubId, userId);
        membership.setMembershipRole(newRole);
        membershipRepository.save(membership);
    }
}
