package com.studentclub.studentclubbackend.services;

import com.studentclub.studentclubbackend.dto.FundingDTO;

public interface FundingApplicationService {
    FundingDTO getFundingByClubId(Long clubId);

    FundingDTO getFundingByFundingId(Long fundingId);

    FundingDTO createFunding(Long clubId, FundingDTO fundingDTO);

    FundingDTO updateFunding(FundingDTO fundingDTO);

    void deleteFunding(Long fundingId);

}
