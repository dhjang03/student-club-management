package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.FundingApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingApplicationRepository extends JpaRepository<FundingApplication, Long> {
}
