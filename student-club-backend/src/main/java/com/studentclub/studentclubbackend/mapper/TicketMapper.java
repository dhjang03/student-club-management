package com.studentclub.studentclubbackend.mapper;

import com.studentclub.studentclubbackend.dto.TicketDTO;
import com.studentclub.studentclubbackend.models.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketDTO toTicketDTO(Ticket ticket) {
        if (ticket == null) return null;

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setFirstName(ticket.getFirstName());
        ticketDTO.setLastName(ticket.getLastName());
        ticketDTO.setEmail(ticket.getEmail());
        return ticketDTO;
    }

    public Ticket toTicket(TicketDTO ticketDTO) {
        if (ticketDTO == null) return null;

        Ticket ticket = new Ticket();
        if (ticketDTO.getId() != null) ticket.setId(ticketDTO.getId());
        ticket.setFirstName(ticketDTO.getFirstName());
        ticket.setLastName(ticketDTO.getLastName());
        ticket.setEmail(ticketDTO.getEmail());
        return ticket;
    }
}
