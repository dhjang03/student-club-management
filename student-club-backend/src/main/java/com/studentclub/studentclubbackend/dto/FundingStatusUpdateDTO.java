package com.studentclub.studentclubbackend.dto;

import com.studentclub.studentclubbackend.constants.ApplicationStatus;
import lombok.Data;

@Data
public class FundingStatusUpdateDTO {
    private ApplicationStatus status;
}
