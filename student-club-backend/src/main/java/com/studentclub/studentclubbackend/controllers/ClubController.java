package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.security.jwt.JwtUtils;
import com.studentclub.studentclubbackend.services.ClubService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clubs")
public class ClubController {

    private final ClubService clubService;
    private final JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<List<ClubDTO>> getAllClubs() {
        return ResponseEntity.ok(clubService.findAll());
    }

    @GetMapping("/my")
    public ResponseEntity<List<ClubDTO>> getMyClubs(
            HttpServletRequest request
    ) {
        String token = jwtUtils.getJwtFromHeader(request);
        Long userId = jwtUtils.getUserIdFromJwtToken(token);
        return ResponseEntity.ok(clubService.findMyClubs(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubDTO> getClubById(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.findById(id));
    }
}
