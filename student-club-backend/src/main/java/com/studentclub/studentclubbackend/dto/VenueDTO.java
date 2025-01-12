package com.studentclub.studentclubbackend.dto;

import lombok.Data;

@Data
public class VenueDTO {
    private long id;
    private int capacity;
    private String name;
    private String address;
}
