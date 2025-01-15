package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.EventDTO;
import com.studentclub.studentclubbackend.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clubs/{clubId}")
public class ClubEventController {

    private final EventService eventService;

    @GetMapping("/events")
    @PreAuthorize("@membershipSecurityService.isMember(#clubId, authentication)")
    public ResponseEntity<List<EventDTO>> getClubEvents(
            @PathVariable Long clubId
    ) {
        return ResponseEntity.ok(eventService.getEventByClubId(clubId));
    }

    @GetMapping("/events/{eventId}")
    @PreAuthorize("@membershipSecurityService.isMember(#clubId, authentication)")
    public ResponseEntity<EventDTO> getClubEventById(
            @PathVariable Long clubId,
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping("/events")
    @PreAuthorize("@membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<EventDTO> createClubEvent(
            @PathVariable Long clubId,
            @RequestBody EventDTO eventDTO
    ) {
        EventDTO newEvent = eventService.createEvent(clubId, eventDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEvent.getId())
                .toUri();
        return ResponseEntity.created(location).body(eventDTO);
    }

    @PutMapping("/events")
    @PreAuthorize("@membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<EventDTO> updateClubEvent(
            @PathVariable String clubId,
            @RequestBody EventDTO eventDTO
    ) {
        return ResponseEntity.ok(eventService.updateEvent(eventDTO));
    }

    @DeleteMapping("/events/{eventId}")
    @PreAuthorize("@membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<Void> deleteClubEvent(
            @PathVariable Long clubId,
            @PathVariable Long eventId
    ) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
