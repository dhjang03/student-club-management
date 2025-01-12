package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.LoginRequestDTO;
import com.studentclub.studentclubbackend.dto.RegisterRequestDTO;
import com.studentclub.studentclubbackend.models.User;
import com.studentclub.studentclubbackend.security.jwt.JwtTokenResponse;

public interface UserService {
    JwtTokenResponse authenticateUser(LoginRequestDTO loginRequest);

    void registerUser(RegisterRequestDTO user);

    User findByUsername(String name);
}
