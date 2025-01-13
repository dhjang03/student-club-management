package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.UserDTO;
import com.studentclub.studentclubbackend.services.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clubs/{clubId}/members")
public class ClubMemberController {

    private ClubService clubService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllMembers(
            @PathVariable Long clubId
    ) {
        return ResponseEntity.ok(clubService.findAllMembers(clubId));
    }

    @PutMapping("/promote")
    public ResponseEntity<Void> promoteToAdmin(
            @PathVariable Long clubId,
            @RequestBody UserDTO userDTO
    ) {
        clubService.promoteMember(clubId, userDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/demote")
    public ResponseEntity<Void> demoteToMember(
            @PathVariable Long clubId,
            @RequestBody UserDTO userDTO
    ) {
        clubService.demoteAdmin(clubId, userDTO);
        return ResponseEntity.ok().build();
    }
}
