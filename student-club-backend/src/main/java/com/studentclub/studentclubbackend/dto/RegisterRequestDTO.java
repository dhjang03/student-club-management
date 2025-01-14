package com.studentclub.studentclubbackend.dto;

import com.studentclub.studentclubbackend.constants.UserRole;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Set<UserRole> roles;
}
