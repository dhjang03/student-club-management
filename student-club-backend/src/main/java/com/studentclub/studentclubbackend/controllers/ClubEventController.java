package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.EventDTO;
import com.studentclub.studentclubbackend.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clubs/{clubId}")
public class ClubEventController {

    private EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getClubEvents(
            @PathVariable Long clubId
    ) {
        return ResponseEntity.ok(eventService.getEventByClubId(clubId));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventDTO> getClubEventById(
            @PathVariable Long clubId,
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping("/events")
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
    public ResponseEntity<EventDTO> updateClubEvent(
            @RequestBody EventDTO eventDTO
    ) {
        return ResponseEntity.ok(eventService.updateEvent(eventDTO));
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteClubEvent(
            @PathVariable Long eventId
    ) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
