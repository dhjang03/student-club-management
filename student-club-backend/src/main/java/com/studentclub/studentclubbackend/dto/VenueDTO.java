package com.studentclub.studentclubbackend.dto;

import lombok.Data;

@Data
public class VenueDTO {
    private Long id;
    private Integer capacity;
    private String name;
    private String address;
}
