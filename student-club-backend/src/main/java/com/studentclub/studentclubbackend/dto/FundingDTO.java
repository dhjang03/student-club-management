package com.studentclub.studentclubbackend.dto;

import com.studentclub.studentclubbackend.constants.ApplicationStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FundingDTO {
    private Long id;
    private BigDecimal amount;
    private String description;
    private Date createdAt;
    private ApplicationStatus status;
}
