package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.FundingApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundingApplicationRepository extends JpaRepository<FundingApplication, Long> {
    Optional<FundingApplication> findByClubId(Long clubId);

    boolean existsByClubId(Long clubId);
}
