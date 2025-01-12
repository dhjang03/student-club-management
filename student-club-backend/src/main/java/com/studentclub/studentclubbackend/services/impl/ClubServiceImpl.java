package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.mapper.ClubMapper;
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
    private final ClubMapper clubMapper;

    public List<ClubDTO> findAll() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream()
                .map(clubMapper::toClubDTO)
                .collect(Collectors.toList());
    }

    public ClubDTO findById(Long id) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        return clubMapper.toClubDTO(club);
    }
}
