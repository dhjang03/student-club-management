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

    private ClubMembershipRepository membershipRepository;
    private ClubRepository clubRepository;
    private UserRepository userRepository;

    @Override
    public ClubMembership addMembership(Long clubId, Long userId, MembershipRole role) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (membershipRepository.findByClubIdAndUserId(clubId, userId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already a member/admin of this club");
        }

        ClubMembership membership = new ClubMembership();
        ClubMembershipId membershipId = new ClubMembershipId(club.getId(), user.getId());
        membership.setId(membershipId);
        membership.setClub(club);
        membership.setUser(user);
        membership.setMembershipRole(role);
        return membershipRepository.save(membership);
    }

    @Override
    public void removeMembership(Long clubId, Long userId) {
        ClubMembership membership = membershipRepository.findByClubIdAndUserId(clubId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membership not found"));
        membershipRepository.delete(membership);
    }

    @Override
    public void promoteMember(Long clubId, Long userId) {
        ClubMembership membership = membershipRepository.findByClubIdAndUserId(clubId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membership not found"));
        membership.setMembershipRole(MembershipRole.ADMIN);
        membershipRepository.save(membership);
    }

    @Override
    public void demoteAdmin(Long clubId, Long userId) {
        ClubMembership membership = membershipRepository.findByClubIdAndUserId(clubId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membership not found"));
        membership.setMembershipRole(MembershipRole.MEMBER);
        membershipRepository.save(membership);
    }

    @Override
    public boolean isMember(Long clubId, Long userId) {
        return membershipRepository.findByClubIdAndUserId(clubId, userId).isPresent();
    }

    @Override
    public boolean isAdmin(Long clubId, Long userId) {
        return membershipRepository.findByClubIdAndUserId(clubId, userId)
                .map(m -> m.getMembershipRole() == MembershipRole.ADMIN)
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
}
