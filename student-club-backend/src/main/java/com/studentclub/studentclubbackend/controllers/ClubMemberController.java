package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.ClubMemberDTO;
import com.studentclub.studentclubbackend.services.ClubService;
import com.studentclub.studentclubbackend.services.MembershipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clubs/{clubId}/members")
public class ClubMemberController {

    private ClubService clubService;
    private MembershipService membershipService;

    @GetMapping
    @PreAuthorize("@membershipSecurityService.isMember(#clubId, authentication)")
    public ResponseEntity<List<ClubMemberDTO>> getAllMembers(
            @PathVariable Long clubId
    ) {
        return ResponseEntity.ok(clubService.findAllMembers(clubId));
    }

    @PutMapping("/promote")
    @PreAuthorize("@membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<Void> promoteToAdmin(
            @PathVariable Long clubId,
            @RequestBody ClubMemberDTO memberDTO
    ) {
        membershipService.promoteMember(clubId, memberDTO.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/demote")
    @PreAuthorize("@membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<Void> demoteToMember(
            @PathVariable Long clubId,
            @RequestBody ClubMemberDTO memberDTO
    ) {
        membershipService.demoteAdmin(clubId, memberDTO.getId());
        return ResponseEntity.ok().build();
    }
}
