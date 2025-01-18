package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.LoginRequestDTO;
import com.studentclub.studentclubbackend.dto.RegisterRequestDTO;
import com.studentclub.studentclubbackend.dto.UserDTO;
import com.studentclub.studentclubbackend.security.jwt.JwtTokenResponse;

public interface UserService {
    JwtTokenResponse authenticateUser(LoginRequestDTO loginRequest);

    void registerUser(RegisterRequestDTO user);

    UserDTO findByUsername(String name);
}
