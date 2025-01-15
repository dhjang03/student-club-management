package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.VenueDTO;
import com.studentclub.studentclubbackend.services.VenueService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/venues")
public class VenueController {

    private final VenueService venueService;

    @GetMapping
    public List<VenueDTO> getAllVenues() {
        return venueService.getAllVenues();
    }
}
