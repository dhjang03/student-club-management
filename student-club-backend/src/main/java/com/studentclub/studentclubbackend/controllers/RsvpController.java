package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.RsvpDTO;
import com.studentclub.studentclubbackend.security.CustomUserDetails;
import com.studentclub.studentclubbackend.services.RsvpService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rsvps")
public class RsvpController {

    private RsvpService rsvpService;

    @GetMapping
    public ResponseEntity<List<RsvpDTO>> getMyRsvps(
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return ResponseEntity.ok(rsvpService.findRsvpsByUserId(userId));
    }

    @GetMapping("/{rsvpId}")
    public ResponseEntity<RsvpDTO> getRsvpById(
            Authentication authentication,
            @PathVariable Long rsvpId
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return ResponseEntity.ok(rsvpService.findRsvpById(userId, rsvpId));
    }

    @DeleteMapping("/{rsvpId}")
    public ResponseEntity<Void> deleteRsvp(
            Authentication authentication,
            @PathVariable Long rsvpId
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        rsvpService.deleteRsvp(userId, rsvpId);
        return ResponseEntity.ok().build();
    }
}
