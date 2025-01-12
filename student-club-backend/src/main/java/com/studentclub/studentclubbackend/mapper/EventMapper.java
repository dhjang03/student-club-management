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
        eventDTO.setHostedBy(event.getClub().getName()); // Assuming Club has a getName() method
        eventDTO.setCapacity(event.getCapacity());
        eventDTO.setNumOfAttendees(event.getNumOfAttendees());
        return eventDTO;
    }
}
