package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.dto.UserDTO;

import java.util.List;

public interface ClubService {
    List<ClubDTO> findAll();

    ClubDTO findById(Long id);

    List<UserDTO> findAllMembers(Long clubId);

    void promoteMember(Long clubId, UserDTO userDTO);

    void demoteAdmin(Long clubId, UserDTO userDTO);
}
