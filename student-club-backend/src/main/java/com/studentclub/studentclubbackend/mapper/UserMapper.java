package com.studentclub.studentclubbackend.mapper;

import com.studentclub.studentclubbackend.dto.UserDTO;
import com.studentclub.studentclubbackend.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        if (user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}
