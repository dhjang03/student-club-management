package com.studentclub.studentclubbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class RsvpDTO {
    private String id;
    private UserDTO responder;
    private EventDTO event;
    private List<TicketDTO> tickets;
}
