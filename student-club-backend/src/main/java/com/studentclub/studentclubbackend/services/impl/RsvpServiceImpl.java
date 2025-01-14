package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.RsvpDTO;
import com.studentclub.studentclubbackend.mapper.RsvpMapper;
import com.studentclub.studentclubbackend.models.Event;
import com.studentclub.studentclubbackend.models.Rsvp;
import com.studentclub.studentclubbackend.models.User;
import com.studentclub.studentclubbackend.repositories.EventRepository;
import com.studentclub.studentclubbackend.repositories.RSVPRepository;
import com.studentclub.studentclubbackend.repositories.UserRepository;
import com.studentclub.studentclubbackend.services.RsvpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RsvpServiceImpl implements RsvpService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private RSVPRepository rsvpRepository;

    private RsvpMapper rsvpMapper;

    @Override
    public List<RsvpDTO> findRsvpsByUserId(Long userId) {
        List<Rsvp> rsvps = rsvpRepository.findByResponderId(userId);
        return rsvps.stream()
                .map(rsvpMapper::toRsvpDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RsvpDTO findRsvpById(Long userId, Long rsvpId) {
        Rsvp rsvp = rsvpRepository.findById(rsvpId)
                .filter(r -> r.isOwnedBy(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RSVP not found or denied"));
        return rsvpMapper.toRsvpDTO(rsvp);
    }

    @Override
    public RsvpDTO createRsvp(Long userId, Long eventId, RsvpDTO rsvpDTO) {
        Rsvp rsvp = rsvpMapper.toRsvp(rsvpDTO);
        User responder = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")
        );

        int numberOfTickets = rsvp.getTickets().size();

        if (!event.updateAttendees(numberOfTickets)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough capacity from Event");
        }

        rsvp.setResponder(responder);
        rsvp.setEvent(event);

        Rsvp newRsvp = rsvpRepository.save(rsvp);
        return rsvpMapper.toRsvpDTO(newRsvp);
    }

    @Override
    public void deleteRsvp(Long userId, Long rsvpId) {
        Rsvp rsvp = rsvpRepository.findById(rsvpId)
                .filter(r -> r.isOwnedBy(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RSVP not found or denied"));
        rsvpRepository.delete(rsvp);
    }
}
