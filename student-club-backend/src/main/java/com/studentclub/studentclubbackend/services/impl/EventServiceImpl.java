package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.EventDTO;
import com.studentclub.studentclubbackend.mapper.EventMapper;
import com.studentclub.studentclubbackend.models.Event;
import com.studentclub.studentclubbackend.repositories.EventRepository;
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

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(long id) {
        Event event = eventRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return eventMapper.toEventDTO(event);
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
}
