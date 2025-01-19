package com.studentclub.studentclubbackend.controllers;

import com.studentclub.studentclubbackend.dto.FundingDTO;
import com.studentclub.studentclubbackend.dto.FundingStatusUpdateDTO;
import com.studentclub.studentclubbackend.services.FundingApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fundings")
public class FundingController {

    private final FundingApplicationService fundingService;

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<List<FundingDTO>> getAllFundings() {
        return ResponseEntity.ok(fundingService.getAllFundings());
    }

    @PutMapping("/{fundingId}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<FundingDTO> updateFundingStatus(
            @PathVariable Long fundingId,
            @RequestBody FundingStatusUpdateDTO newStatus
    ) {
        return ResponseEntity.ok(fundingService.updateFundingStatus(fundingId, newStatus));
    }
}
