package com.studentclub.studentclubbackend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private Date date;
    private VenueDTO venue;
    private BigDecimal cost;
    private int capacity;
    private int numOfAttendees;
}
