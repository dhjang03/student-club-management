package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.constants.ApplicationStatus;
import com.studentclub.studentclubbackend.dto.FundingDTO;
import com.studentclub.studentclubbackend.dto.FundingStatusUpdateDTO;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FundingApplicationServiceImpl implements FundingApplicationService {

    private final FundingApplicationRepository fundingRepository;
    private final ClubRepository clubRepository;
    private final FundingMapper fundingMapper;

    @Override
    public List<FundingDTO> getAllFundings() {
        List<FundingApplication> fundingApplications = fundingRepository.findAll();
        return fundingApplications.stream()
                .map(fundingMapper::toFundingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FundingDTO updateFundingStatus(Long fundingId, FundingStatusUpdateDTO status) {
        FundingApplication funding = findFundingByIdOrThrow(fundingId);
        funding.setStatus(status.getStatus());
        fundingRepository.save(funding);
        return fundingMapper.toFundingDTO(funding);
    }

    @Override
    public FundingDTO getFundingByClubId(Long clubId) {
        FundingApplication funding = findFundingByClubIdOrThrow(clubId);
        return fundingMapper.toFundingDTO(funding);
    }

    @Override
    public FundingDTO getFundingByFundingId(Long fundingId) {
        FundingApplication funding = findFundingByIdOrThrow(fundingId);
        return fundingMapper.toFundingDTO(funding);
    }

    @Override
    public FundingDTO createFunding(Long clubId, FundingDTO fundingDTO) {
        if (fundingRepository.existsByClubId(clubId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Funding Application Already Exists");
        }

        Club club = findClubByIdOrThrow(clubId);
        FundingApplication funding = buildFundingApplication(fundingDTO, club);
        FundingApplication savedFunding = fundingRepository.save(funding);

        return fundingMapper.toFundingDTO(savedFunding);
    }

    @Override
    public FundingDTO updateFunding(FundingDTO fundingDTO) {
        FundingApplication existingFunding = findFundingByIdOrThrow(fundingDTO.getId());

        validateFundingStatus(existingFunding);

        updateFundingDetails(existingFunding, fundingDTO);

        if (fundingDTO.getStatus() == ApplicationStatus.APPROVED) {
            existingFunding.getClub().addFunds(fundingDTO.getAmount());
        }

        FundingApplication updatedFunding = fundingRepository.save(existingFunding);
        return fundingMapper.toFundingDTO(updatedFunding);
    }

    @Override
    public void deleteFunding(Long fundingId) {
        FundingApplication funding = findFundingByIdOrThrow(fundingId);
        fundingRepository.delete(funding);
    }

    private FundingApplication findFundingByIdOrThrow(Long fundingId) {
        return fundingRepository.findById(fundingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funding Application Not Found."));
    }

    private FundingApplication findFundingByClubIdOrThrow(Long clubId) {
        return fundingRepository.findByClubId(clubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funding Application Not Found."));
    }

    private Club findClubByIdOrThrow(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club Not Found."));
    }

    private FundingApplication buildFundingApplication(FundingDTO fundingDTO, Club club) {
        FundingApplication funding = fundingMapper.toFundingApplication(fundingDTO);
        funding.setClub(club);
        funding.setCreatedAt(new Date());
        return funding;
    }

    private void validateFundingStatus(FundingApplication funding) {
        if (funding.getStatus() == ApplicationStatus.APPROVED || funding.getStatus() == ApplicationStatus.REJECTED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Funding Application Already Approved/Rejected");
        }
    }

    private void updateFundingDetails(FundingApplication funding, FundingDTO fundingDTO) {
        funding.setDescription(fundingDTO.getDescription());
        funding.setAmount(fundingDTO.getAmount());
        funding.setStatus(fundingDTO.getStatus());
    }
}
