package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.LoginRequestDTO;
import com.studentclub.studentclubbackend.dto.RegisterRequestDTO;
import com.studentclub.studentclubbackend.dto.UserDTO;
import com.studentclub.studentclubbackend.mapper.UserMapper;
import com.studentclub.studentclubbackend.models.User;
import com.studentclub.studentclubbackend.repositories.UserRepository;
import com.studentclub.studentclubbackend.security.CustomUserDetails;
import com.studentclub.studentclubbackend.security.jwt.JwtTokenResponse;
import com.studentclub.studentclubbackend.security.jwt.JwtUtils;
import com.studentclub.studentclubbackend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @Override
    public JwtTokenResponse authenticateUser(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        return new JwtTokenResponse(jwt);
    }

    @Override
    public void registerUser(RegisterRequestDTO registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username is already taken: " + registerRequest.getUsername());
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already registered: " + registerRequest.getEmail());
        }
        User user = buildUserFromRegisterRequest(registerRequest);
        userRepository.save(user);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));
        return userMapper.toUserDTO(user);
    }

    private Authentication authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private User buildUserFromRegisterRequest(RegisterRequestDTO registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRoles(registerRequest.getRoles());
        return user;
    }
}
