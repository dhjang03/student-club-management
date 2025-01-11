package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.services.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/clubs")
public class ClubController {
    private final ClubService clubService;

    @GetMapping
    public List<ClubDTO> getAllClubs() {
        System.out.println("getAllClubs Controller");
        return clubService.findAll();
    }

    @GetMapping("/{id}")
    public ClubDTO getClubById(@PathVariable Long id) {
        return clubService.findById(id);
    }
}
