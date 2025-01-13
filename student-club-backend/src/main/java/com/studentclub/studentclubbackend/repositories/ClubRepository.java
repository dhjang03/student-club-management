package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.Club;
import com.studentclub.studentclubbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findAllByAdminsContaining(User admin);
}
