package com.studentclub.studentclubbackend.security;

import com.studentclub.studentclubbackend.services.MembershipService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MembershipSecurityService {

    private MembershipService membershipService;

    public boolean isMember(Long clubId, Authentication authentication) {
        Long currentUserId = getUserIdFromAuthentication(authentication);
        return membershipService.isMember(clubId, currentUserId);
    }

    public boolean isAdmin(Long clubId, Authentication authentication) {
        Long currentUserId = getUserIdFromAuthentication(authentication);
        return membershipService.isAdmin(clubId, currentUserId);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        log.info("getUserIdFromAuthentication: {}", authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}
