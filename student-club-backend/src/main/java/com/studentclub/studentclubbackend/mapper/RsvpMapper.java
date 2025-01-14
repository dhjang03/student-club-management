package com.studentclub.studentclubbackend.mapper;

import com.studentclub.studentclubbackend.dto.RsvpDTO;
import com.studentclub.studentclubbackend.dto.TicketDTO;
import com.studentclub.studentclubbackend.models.Rsvp;
import com.studentclub.studentclubbackend.models.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RsvpMapper {

    private UserMapper userMapper;
    private TicketMapper ticketMapper;
    private EventMapper eventMapper;

    public RsvpDTO toRsvpDTO(Rsvp rsvp) {
        if (rsvp == null) return null;

        RsvpDTO rsvpDTO = new RsvpDTO();
        rsvpDTO.setId(String.valueOf(rsvp.getId()));

        rsvpDTO.setResponder(userMapper.toUserDTO(rsvp.getResponder()));

        List<TicketDTO> ticketDTOs = rsvp.getTickets().stream()
                .map(ticketMapper::toTicketDTO)
                .collect(Collectors.toList());
        rsvpDTO.setTickets(ticketDTOs);

        rsvpDTO.setEvent(eventMapper.toEventDTO(rsvp.getEvent()));

        return rsvpDTO;
    }

    public Rsvp toRsvp(RsvpDTO rsvpDTO) {
        if (rsvpDTO  == null) return null;

        Rsvp rsvp = new Rsvp();

        Set<Ticket> tickets = rsvpDTO.getTickets().stream()
                .map(ticketMapper::toTicket)
                .peek(ticket -> ticket.setRsvp(rsvp))
                .collect(Collectors.toSet());

        rsvp.setTickets(tickets);
        return rsvp;
    }
}
