package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.VenueDTO;
import com.studentclub.studentclubbackend.mapper.VenueMapper;
import com.studentclub.studentclubbackend.repositories.VenueRepository;
import com.studentclub.studentclubbackend.services.VenueService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll().stream()
                .map(venueMapper::toVenueDTO)
                .collect(Collectors.toList());
    }
}
