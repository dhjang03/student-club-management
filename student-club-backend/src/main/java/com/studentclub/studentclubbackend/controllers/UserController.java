package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.UserDTO;
import com.studentclub.studentclubbackend.security.CustomUserDetails;
import com.studentclub.studentclubbackend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return ResponseEntity.ok(userService.findByUsername(username));
    }
}
