package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.constants.Roles;
import com.studentclub.studentclubbackend.dto.ClubDTO;
import com.studentclub.studentclubbackend.dto.UserDTO;
import com.studentclub.studentclubbackend.mapper.ClubMapper;
import com.studentclub.studentclubbackend.mapper.UserMapper;
import com.studentclub.studentclubbackend.models.Club;
import com.studentclub.studentclubbackend.models.User;
import com.studentclub.studentclubbackend.repositories.ClubRepository;
import com.studentclub.studentclubbackend.repositories.UserRepository;
import com.studentclub.studentclubbackend.services.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {

    private ClubRepository clubRepository;
    private UserRepository userRepository;

    private ClubMapper clubMapper;
    private UserMapper userMapper;

    @Override
    public List<ClubDTO> findAll() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream()
                .map(clubMapper::toClubDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClubDTO findById(Long id) {
        Club club = getClubById((id));
        return clubMapper.toClubDTO(club);
    }

    @Override
    public List<UserDTO> findAllMembers(Long clubId) {
        Club club = getClubById(clubId);

        Set<User> allMembers = new HashSet<>();
        allMembers.addAll(club.getAdmins());
        allMembers.addAll(club.getMembers());

        return allMembers.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void promoteMember(Long clubId, UserDTO userDTO) {
        Club club = getClubById(clubId);
        Long userId = userDTO.getId();

        User member = getUserInSet(club.getMembers(), userId);

        club.getMembers().remove(member);
        club.getAdmins().add(member);

        System.out.println(club.getMembers());
        System.out.println(club.getAdmins());

        member.getRoles().add(Roles.ROLE_STUDENT_ADMIN);

        userRepository.save(member);
        clubRepository.save(club);
    }

    @Override
    public void demoteAdmin(Long clubId, UserDTO userDTO) {
        Club club = getClubById(clubId);
        Long userId = userDTO.getId();

        User admin = getUserInSet(club.getAdmins(), userId);

        club.getAdmins().remove(admin);
        club.getMembers().add(admin);
        clubRepository.save(club);

        System.out.println(club.getMembers());
        System.out.println(club.getAdmins());

        List<Club> clubsAdministeredByUser = clubRepository.findAllByAdminsContaining(admin);
        if (clubsAdministeredByUser.isEmpty()) {
            admin.getRoles().remove(Roles.ROLE_STUDENT_ADMIN);
            userRepository.save(admin);
        }
    }

    private Club getClubById(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found")
        );
    }

    private User getUserInSet(Set<User> users, Long userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not Member of the Club."));
    }
}
