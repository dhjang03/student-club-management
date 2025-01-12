package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.EventDTO;

import java.util.List;

public interface EventService {
    List<EventDTO> getAllEvents();

    EventDTO getEventById(long id);

    List<EventDTO> searchEventsByKeyword(String keyword);

    List<EventDTO> getEventByClubId(Long id);

    EventDTO createEvent(Long clubId, EventDTO eventDTO);

    EventDTO updateEvent(EventDTO eventDTO);

    void deleteEvent(Long eventId);
}
