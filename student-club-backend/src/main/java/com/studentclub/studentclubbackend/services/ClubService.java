package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.ClubDTO;

import java.util.List;

public interface ClubService {
    public List<ClubDTO> findAll();
    public ClubDTO findById(Long id);
}
