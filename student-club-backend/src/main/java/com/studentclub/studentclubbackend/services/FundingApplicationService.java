package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.FundingDTO;
import com.studentclub.studentclubbackend.dto.FundingStatusUpdateDTO;

import java.util.List;

public interface FundingApplicationService {
    FundingDTO getFundingByClubId(Long clubId);

    FundingDTO getFundingByFundingId(Long fundingId);

    FundingDTO createFunding(Long clubId, FundingDTO fundingDTO);

    FundingDTO updateFunding(FundingDTO fundingDTO);

    void deleteFunding(Long fundingId);

    List<FundingDTO> getAllFundings();

    FundingDTO updateFundingStatus(Long fundingId, FundingStatusUpdateDTO status);
}
