package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.RsvpDTO;

import java.util.List;

public interface RsvpService {
    List<RsvpDTO> findRsvpsByUserId(Long userId);

    RsvpDTO findRsvpById(Long userId, Long rsvpId);

    RsvpDTO createRsvp(Long userId, Long evnetId, RsvpDTO rsvpDTO);

    void deleteRsvp(Long userId, Long rsvpId);
}
