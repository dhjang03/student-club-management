package com.studentclub.studentclubbackend.mapper;

import com.studentclub.studentclubbackend.dto.FundingDTO;
import com.studentclub.studentclubbackend.models.FundingApplication;
import org.springframework.stereotype.Component;

@Component
public class FundingMapper {

    public FundingDTO toFundingDTO(FundingApplication funding) {
        if (funding == null) return null;

        FundingDTO fundingDTO = new FundingDTO();
        fundingDTO.setId(funding.getId());
        fundingDTO.setDescription(funding.getDescription());
        fundingDTO.setAmount(funding.getAmount());
        fundingDTO.setCreatedAt(funding.getCreatedAt());
        fundingDTO.setStatus(funding.getStatus());
        return fundingDTO;
    }

    public FundingApplication toFundingApplication(FundingDTO fundingDTO) {
        if (fundingDTO == null) return null;

        FundingApplication fundingApplication = new FundingApplication();
        if (fundingDTO.getId() != null) fundingApplication.setId(fundingDTO.getId());
        if (fundingApplication.getCreatedAt() != null) fundingApplication.setCreatedAt(fundingDTO.getCreatedAt());
        fundingApplication.setDescription(fundingDTO.getDescription());
        fundingApplication.setAmount(fundingDTO.getAmount());
        fundingApplication.setStatus(fundingDTO.getStatus());
        return fundingApplication;
    }
}
