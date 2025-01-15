package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.EventDTO;
import com.studentclub.studentclubbackend.dto.RsvpDTO;
import com.studentclub.studentclubbackend.security.CustomUserDetails;
import com.studentclub.studentclubbackend.services.EventService;
import com.studentclub.studentclubbackend.services.RsvpService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;
    private final RsvpService rsvpService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventById(
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<EventDTO>> searchEvents(
        @RequestParam String keyword
    ) {
        return ResponseEntity.ok(eventService.searchEventsByKeyword(keyword));
    }

    @PostMapping("/{eventId}/rsvp")
    public ResponseEntity<RsvpDTO> createRsvp(
            Authentication authentication,
            @PathVariable Long eventId,
            @RequestBody RsvpDTO rsvpDTO
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();

        return ResponseEntity.ok(rsvpService.createRsvp(userId, eventId, rsvpDTO));
    }
}
