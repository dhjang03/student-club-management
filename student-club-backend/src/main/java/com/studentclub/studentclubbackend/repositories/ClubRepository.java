package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
