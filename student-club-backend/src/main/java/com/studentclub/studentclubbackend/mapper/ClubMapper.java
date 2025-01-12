package com.studentclub.studentclubbackend.mapper;

import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.models.Club;
import org.springframework.stereotype.Component;

@Component
public class ClubMapper {

    public ClubDTO toClubDTO(Club club) {
        if (club == null) return null;

        ClubDTO clubDTO = new ClubDTO();
        clubDTO.setId(club.getId());
        clubDTO.setName(club.getName());
        clubDTO.setDescription(club.getDescription());
        clubDTO.setFunds(club.getFunds());
        return clubDTO;
    }
}
