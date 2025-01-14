package com.studentclub.studentclubbackend.mapper;

import com.studentclub.studentclubbackend.dto.EventDTO;
import com.studentclub.studentclubbackend.models.Event;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventMapper {

    private final VenueMapper venueMapper;

    public EventDTO toEventDTO(Event event) {
        if (event == null) return null;

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setDate(event.getDate());
        eventDTO.setVenue(venueMapper.toVenueDTO(event.getVenue()));
        eventDTO.setCost(event.getCost());
        eventDTO.setCapacity(event.getCapacity());
        eventDTO.setNumOfAttendees(event.getNumOfAttendees());
        eventDTO.setCreatedAt(event.getCreatedAt());
        return eventDTO;
    }

    public Event toEvent(EventDTO eventDTO) {
        if (eventDTO == null) return null;

        Event event = new Event();

        if (eventDTO.getId() != null) event.setId(eventDTO.getId());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate());
        event.setVenue(venueMapper.toVenue(eventDTO.getVenue()));
        event.setCost(eventDTO.getCost());
        event.setCapacity(eventDTO.getCapacity());
        event.setNumOfAttendees(eventDTO.getNumOfAttendees());
        return event;
    }
}
