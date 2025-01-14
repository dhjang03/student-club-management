package com.studentclub.studentclubbackend.dto;

import lombok.Data;

@Data
public class ClubMemberDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String membership;
}
