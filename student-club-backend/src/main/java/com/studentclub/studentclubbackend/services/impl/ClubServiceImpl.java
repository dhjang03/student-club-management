package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.dto.ClubMemberDTO;
import com.studentclub.studentclubbackend.mapper.ClubMapper;
import com.studentclub.studentclubbackend.mapper.ClubMemberMapper;
import com.studentclub.studentclubbackend.models.Club;
import com.studentclub.studentclubbackend.models.ClubMembership;
import com.studentclub.studentclubbackend.repositories.ClubRepository;
import com.studentclub.studentclubbackend.services.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {

    private ClubRepository clubRepository;

    private ClubMapper clubMapper;
    private ClubMemberMapper clubMemberMapper;

    @Override
    public List<ClubDTO> findAll() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream()
                .map(clubMapper::toClubDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClubDTO findById(Long id) {
        Club club = getClubById((id));
        return clubMapper.toClubDTO(club);
    }

    @Override
    public List<ClubMemberDTO> findAllMembers(Long clubId) {
        Club club = getClubById(clubId);

        Set<ClubMembership> memberships = club.getMemberships();

        return memberships.stream()
                .map(clubMemberMapper::toClubMemberDTO)
                .collect(Collectors.toList());
    }

    private Club getClubById(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
    }
}
