package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.RsvpDTO;
import com.studentclub.studentclubbackend.mapper.RsvpMapper;
import com.studentclub.studentclubbackend.mapper.TicketMapper;
import com.studentclub.studentclubbackend.models.Event;
import com.studentclub.studentclubbackend.models.Rsvp;
import com.studentclub.studentclubbackend.models.Ticket;
import com.studentclub.studentclubbackend.models.User;
import com.studentclub.studentclubbackend.repositories.EventRepository;
import com.studentclub.studentclubbackend.repositories.RSVPRepository;
import com.studentclub.studentclubbackend.repositories.UserRepository;
import com.studentclub.studentclubbackend.services.RsvpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RsvpServiceImpl implements RsvpService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RSVPRepository rsvpRepository;
    private final RsvpMapper rsvpMapper;
    private final TicketMapper ticketMapper;

    @Override
    public List<RsvpDTO> findRsvpsByUserId(Long userId) {
        return rsvpRepository.findByResponderId(userId).stream()
                .map(rsvpMapper::toRsvpDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RsvpDTO findRsvpById(Long userId, Long rsvpId) {
        Rsvp rsvp = findRsvpByIdAndUserOrThrow(rsvpId, userId);
        return rsvpMapper.toRsvpDTO(rsvp);
    }

    @Override
    @Transactional
    public RsvpDTO createRsvp(Long userId, Long eventId, RsvpDTO rsvpDTO) {
        User responder = findUserByIdOrThrow(userId);
        Event event = findEventForUpdateOrThrow(eventId);

        Rsvp rsvp = buildRsvp(rsvpDTO, responder, event);

        int numberOfTickets = rsvp.getTickets().size();
        if (!event.updateAttendees(numberOfTickets)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough capacity in Event");
        }

        Rsvp newRsvp = rsvpRepository.save(rsvp);
        return rsvpMapper.toRsvpDTO(newRsvp);
    }

    @Override
    public void deleteRsvp(Long userId, Long rsvpId) {
        Rsvp rsvp = findRsvpByIdAndUserOrThrow(rsvpId, userId);
        rsvpRepository.delete(rsvp);
    }

    private User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private Event findEventForUpdateOrThrow(Long eventId) {
        return eventRepository.findEventForUpdate(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    private Rsvp findRsvpByIdAndUserOrThrow(Long rsvpId, Long userId) {
        return rsvpRepository.findById(rsvpId)
                .filter(rsvp -> rsvp.isOwnedBy(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RSVP not found or access denied"));
    }

    private Rsvp buildRsvp(RsvpDTO rsvpDTO, User responder, Event event) {
        Rsvp rsvp = rsvpMapper.toRsvp(rsvpDTO);
        rsvp.setResponder(responder);
        rsvp.setEvent(event);
        rsvp.setCreatedAt(new Date());

        Set<Ticket> tickets = rsvpDTO.getTickets().stream()
                .map(ticketDTO -> {
                    Ticket ticket = ticketMapper.toTicket(ticketDTO);
                    ticket.setRsvp(rsvp);
                    return ticket;
                })
                .collect(Collectors.toSet());

        rsvp.setTickets(tickets);
        return rsvp;
    }
}
