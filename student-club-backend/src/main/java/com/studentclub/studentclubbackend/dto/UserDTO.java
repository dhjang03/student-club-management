package com.studentclub.studentclubbackend.dto;

import com.studentclub.studentclubbackend.constants.UserRole;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<UserRole> roles;
}
