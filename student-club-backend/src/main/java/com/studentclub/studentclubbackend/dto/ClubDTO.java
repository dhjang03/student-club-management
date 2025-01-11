package com.studentclub.studentclubbackend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClubDTO {
    private long id;
    private String name;
    private String description;
    private BigDecimal funds;
}
