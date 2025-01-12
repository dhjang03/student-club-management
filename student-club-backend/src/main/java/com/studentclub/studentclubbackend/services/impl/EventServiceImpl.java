package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.EventDTO;
import com.studentclub.studentclubbackend.mapper.EventMapper;
import com.studentclub.studentclubbackend.models.Club;
import com.studentclub.studentclubbackend.models.Event;
import com.studentclub.studentclubbackend.models.Venue;
import com.studentclub.studentclubbackend.repositories.ClubRepository;
import com.studentclub.studentclubbackend.repositories.EventRepository;
import com.studentclub.studentclubbackend.repositories.VenueRepository;
import com.studentclub.studentclubbackend.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ClubRepository clubRepository;
    private final VenueRepository venueRepository;

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> searchEventsByKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getAllEvents();
        }

        List<Event> events = eventRepository.findByTitleContainingIgnoreCase(keyword);
        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(long id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")
        );
        return eventMapper.toEventDTO(event);
    }

    @Override
    public List<EventDTO> getEventByClubId(Long id) {
        List<Event> events = eventRepository.findByClubId(id);
        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO createEvent(Long clubId, EventDTO eventDTO) {
        Event event = eventMapper.toEvent(eventDTO);
        Club club = clubRepository.findById(clubId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found")
        );
        event.setClub(club);
        Event newEvent = eventRepository.save(event);
        return eventMapper.toEventDTO(newEvent);
    }

    @Override
    public EventDTO updateEvent(EventDTO eventDTO) {
        Event existingEvent = eventRepository.findById(eventDTO.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")
        );
        Venue venue = venueRepository.findById(eventDTO.getVenue().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found")
        );

        existingEvent.setTitle(eventDTO.getTitle());
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setDate(eventDTO.getDate());
        existingEvent.setCost(eventDTO.getCost());
        existingEvent.setCapacity(eventDTO.getCapacity());
        existingEvent.setVenue(venue);

        Event updatedEvent = eventRepository.save(existingEvent);
        return eventMapper.toEventDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")
        );
        eventRepository.delete(event);
    }
}
