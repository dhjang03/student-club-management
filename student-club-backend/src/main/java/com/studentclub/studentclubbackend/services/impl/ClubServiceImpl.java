package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.models.Club;
import com.studentclub.studentclubbackend.repositories.ClubRepository;
import com.studentclub.studentclubbackend.services.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;

    public List<ClubDTO> findAll() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream()
                .map(club -> {
                    ClubDTO dto = new ClubDTO();
                    dto.setId(club.getId());
                    dto.setName(club.getName());
                    dto.setDescription(club.getDescription());
                    dto.setFunds(club.getFunds());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ClubDTO findById(Long id) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));

        ClubDTO clubDTO = new ClubDTO();
        clubDTO.setId(club.getId());
        clubDTO.setName(club.getName());
        clubDTO.setDescription(club.getDescription());
        clubDTO.setFunds(club.getFunds());

        return clubDTO;
    }
}
