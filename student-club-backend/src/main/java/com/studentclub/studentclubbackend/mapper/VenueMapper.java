package com.studentclub.studentclubbackend.mapper;

import com.studentclub.studentclubbackend.dto.VenueDTO;
import com.studentclub.studentclubbackend.models.Venue;
import org.springframework.stereotype.Component;

@Component
public class VenueMapper {

    public VenueDTO toVenueDTO(Venue venue) {
        if (venue == null) return null;

        VenueDTO venueDTO = new VenueDTO();
        venueDTO.setId(venue.getId());
        venueDTO.setName(venue.getName());
        venueDTO.setAddress(venue.getAddress());
        venueDTO.setCapacity(venue.getCapacity());
        return venueDTO;
    }
}
