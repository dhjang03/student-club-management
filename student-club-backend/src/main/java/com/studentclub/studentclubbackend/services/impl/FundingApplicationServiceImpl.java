package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.constants.ApplicationStatus;
import com.studentclub.studentclubbackend.dto.FundingDTO;
import com.studentclub.studentclubbackend.mapper.FundingMapper;
import com.studentclub.studentclubbackend.models.Club;
import com.studentclub.studentclubbackend.models.FundingApplication;
import com.studentclub.studentclubbackend.repositories.ClubRepository;
import com.studentclub.studentclubbackend.repositories.FundingApplicationRepository;
import com.studentclub.studentclubbackend.services.FundingApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@AllArgsConstructor
public class FundingApplicationServiceImpl implements FundingApplicationService {

    private FundingApplicationRepository fundingRepository;
    private ClubRepository clubRepository;

    private FundingMapper fundingMapper;

    @Override
    public FundingDTO getFundingByClubId(Long clubId) {
        FundingApplication funding = fundingRepository.findByClubId(clubId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funding Application Not Found.")
        );
        return fundingMapper.toFundingDTO(funding);
    }

    @Override
    public FundingDTO getFundingByFundingId(Long fundingId) {
        FundingApplication funding = fundingRepository.findById(fundingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funding Application Not Found.")
        );
        return fundingMapper.toFundingDTO(funding);
    }

    @Override
    public FundingDTO createFunding(Long clubId, FundingDTO fundingDTO) {
        FundingApplication funding = fundingMapper.toFundingApplication(fundingDTO);
        Club club = clubRepository.findById(clubId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club Not Found.")
        );

        if (fundingRepository.existsByClubId(clubId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Funding Application Already Exists");
        }

        funding.setClub(club);
        funding.setCreatedAt(new Date());
        FundingApplication newFunding = fundingRepository.save(funding);
        return fundingMapper.toFundingDTO(newFunding);
    }

    @Override
    public FundingDTO updateFunding(FundingDTO fundingDTO) {
        FundingApplication existingFunding = fundingRepository.findById(fundingDTO.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funding Application Not Found.")
        );

        if (existingFunding.getStatus() == ApplicationStatus.APPROVED
                || existingFunding.getStatus() == ApplicationStatus.REJECTED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Funding Application Already Approved/Rejected");
        }

        Club club = existingFunding.getClub();
        if (fundingDTO.getStatus() == ApplicationStatus.APPROVED) {
            club.addFunds(fundingDTO.getAmount());
        }

        existingFunding.setDescription(fundingDTO.getDescription());
        existingFunding.setAmount(fundingDTO.getAmount());
        existingFunding.setStatus(fundingDTO.getStatus());

        FundingApplication updatedFunding = fundingRepository.save(existingFunding);
        return fundingMapper.toFundingDTO(updatedFunding);
    }

    @Override
    public void deleteFunding(Long fundingId) {
        FundingApplication funding = fundingRepository.findById(fundingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funding Application Not Found.")
        );
        fundingRepository.delete(funding);
    }
}
