package com.studentclub.studentclubbackend.mapper;

import com.studentclub.studentclubbackend.dto.ClubMemberDTO;
import com.studentclub.studentclubbackend.models.ClubMembership;
import org.springframework.stereotype.Component;

@Component
public class ClubMemberMapper {

    public ClubMemberDTO toClubMemberDTO(ClubMembership membership) {
        if (membership == null) return null;

        ClubMemberDTO memberDTO = new ClubMemberDTO();
        memberDTO.setId(membership.getUser().getId());
        memberDTO.setFirstName(membership.getUser().getFirstName());
        memberDTO.setLastName(membership.getUser().getLastName());
        memberDTO.setEmail(membership.getUser().getEmail());
        memberDTO.setMembership(membership.getMembershipRole().toString());
        return memberDTO;
    }
}
