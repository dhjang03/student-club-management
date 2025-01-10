package com.studentclub.studentclubbackend.repositories;

import com.studentclub.studentclubbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
