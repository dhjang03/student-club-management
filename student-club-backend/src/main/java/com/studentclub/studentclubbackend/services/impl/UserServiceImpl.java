package com.studentclub.studentclubbackend.services.impl;

import com.studentclub.studentclubbackend.dto.LoginRequestDTO;
import com.studentclub.studentclubbackend.dto.RegisterRequestDTO;
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

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Override
    public JwtTokenResponse authenticateUser(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        return new JwtTokenResponse(jwt);
    }

    @Override
    public void registerUser(RegisterRequestDTO registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRoles(registerRequest.getRoles());
        userRepository.save(user);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + name));
    }
}
