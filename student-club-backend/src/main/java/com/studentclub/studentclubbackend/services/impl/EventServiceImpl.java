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

import java.math.BigDecimal;
import java.util.Date;
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
        return eventRepository.findAll().stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> searchEventsByKeyword(String keyword) {
        List<Event> events = (keyword == null || keyword.isBlank())
                ? eventRepository.findAll()
                : eventRepository.findByTitleContainingIgnoreCase(keyword);

        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(Long id) {
        return eventMapper.toEventDTO(
                findEventByIdOrThrow(id)
        );
    }

    @Override
    public List<EventDTO> getEventByClubId(Long clubId) {
        return eventRepository.findByClubId(clubId).stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO createEvent(Long clubId, EventDTO eventDTO) {
        Venue venue = validateVenueAvailability(eventDTO);
        Club club = validateClubFunds(clubId, eventDTO);
        Event event = buildEvent(eventDTO, club, venue);
        Event savedEvent = eventRepository.save(event);

        return eventMapper.toEventDTO(savedEvent);
    }

    @Override
    public EventDTO updateEvent(EventDTO eventDTO) {
        Event existingEvent = findEventByIdOrThrow(eventDTO.getId());
        Venue venue = findVenueByIdOrThrow(eventDTO.getVenue().getId());
        Club club = existingEvent.getClub();

        updateClubFunds(club, existingEvent.getCost(), eventDTO.getCost());
        updateEventDetails(existingEvent, eventDTO, venue);

        Event updatedEvent = eventRepository.save(existingEvent);
        return eventMapper.toEventDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = findEventByIdOrThrow(eventId);
        if (event.getDate().after(new Date())) {
            Club club = event.getClub();
            club.addFunds(event.getCost());
        }
        eventRepository.delete(event);
    }

    private Venue validateVenueAvailability(EventDTO eventDTO) {
        Venue venue = findVenueByIdOrThrow(eventDTO.getVenue().getId());

        if (isVenueBooked(venue, eventDTO.getDate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Venue is not available");
        }

        return venue;
    }

    private Club validateClubFunds(Long clubId, EventDTO eventDTO) {
        Club club = findClubByIdOrThrow(clubId);

        if (club.getFunds().compareTo(eventDTO.getCost()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds to create event");
        }

        club.removeFunds(eventDTO.getCost());
        return club;
    }

    private boolean isVenueBooked(Venue venue, Date date) {
        return venue.getEvents().stream()
                .anyMatch(event -> event.getDate().equals(date));
    }

    private void updateClubFunds(Club club, BigDecimal oldCost, BigDecimal newCost) {
        BigDecimal delta = newCost.subtract(oldCost);

        if (delta.compareTo(BigDecimal.ZERO) > 0) {
            club.removeFunds(delta);
        } else {
            club.addFunds(delta.abs());
        }
    }

    private void updateEventDetails(Event event, EventDTO eventDTO, Venue venue) {
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate());
        event.setCost(eventDTO.getCost());
        event.setCapacity(eventDTO.getCapacity());
        event.setVenue(venue);
    }

    private Event buildEvent(EventDTO eventDTO, Club club, Venue venue) {
        Event event = eventMapper.toEvent(eventDTO);
        event.setClub(club);
        event.setVenue(venue);
        event.setCreatedAt(new Date());
        return event;
    }

    private Event findEventByIdOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    private Venue findVenueByIdOrThrow(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));
    }

    private Club findClubByIdOrThrow(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
    }
}
