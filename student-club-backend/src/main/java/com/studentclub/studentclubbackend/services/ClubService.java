package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.dto.ClubMemberDTO;

import java.util.List;

public interface ClubService {
    List<ClubDTO> findAll();

    ClubDTO findById(Long id);

    List<ClubMemberDTO> findAllMembers(Long clubId);
}
