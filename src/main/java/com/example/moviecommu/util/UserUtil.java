package com.example.moviecommu.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class                    UserUtil {
    public Long getCurrentUsername() {// userId 반환
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // 인증되지 않은 사용자 처리
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Long.parseLong(userDetails.getUsername());
    }

    public String getCurrentUserRole() { //user role 반환
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // 인증되지 않은 사용자 처리
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("ROLE_USER");
    }
}
