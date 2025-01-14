package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.FundingDTO;
import com.studentclub.studentclubbackend.services.FundingApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clubs/{clubId}/funding")
public class ClubFundingController {

    private FundingApplicationService fundingService;

    @GetMapping
    @PreAuthorize("hasRole('STAFF') || @membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<FundingDTO> getClubFunding(
            @PathVariable Long clubId
    ) {
        return ResponseEntity.ok(fundingService.getFundingByClubId(clubId));
    }

    @GetMapping("/{fundingId}")
    @PreAuthorize("hasRole('STAFF') || @membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<FundingDTO> getClubFundingByFundingId(
            @PathVariable Long clubId,
            @PathVariable Long fundingId
    ) {
        return ResponseEntity.ok(fundingService.getFundingByFundingId(fundingId));
    }

    @PostMapping
    @PreAuthorize("hasRole('STAFF') || @membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<FundingDTO> createFunding(
            @PathVariable Long clubId,
            @RequestBody FundingDTO fundingDTO
    ) {
        FundingDTO newFunding = fundingService.createFunding(clubId, fundingDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFunding.getId())
                .toUri();
        return ResponseEntity.created(location).body(newFunding);
    }

    @PutMapping
    @PreAuthorize("hasRole('STAFF') || @membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<FundingDTO> updateFunding(
            @PathVariable Long clubId,
            @RequestBody FundingDTO fundingDTO
    ) {
        return ResponseEntity.ok(fundingService.updateFunding(fundingDTO));
    }

    @DeleteMapping("/{fundingId}")
    @PreAuthorize("hasRole('STAFF') || @membershipSecurityService.isAdmin(#clubId, authentication)")
    public ResponseEntity<Void> deleteFunding(
            @PathVariable Long clubId,
            @PathVariable Long fundingId
    ) {
        fundingService.deleteFunding(fundingId);
        return ResponseEntity.noContent().build();
    }
}
