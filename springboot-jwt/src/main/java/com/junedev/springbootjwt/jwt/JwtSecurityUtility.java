package com.junedev.springbootjwt.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtSecurityUtility {
    private JwtSecurityUtility() {

    }

    public static Long getCurrentMemberId() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No info in security context");
        }

        return Long.parseLong(authentication.getName());
    }

    public static Object getCurrentPrincipal() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No info in security context");
        }
        return authentication.getPrincipal();
    }

    public static Object getCurrentAuthorities() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No info in security context");
        }
        return authentication.getAuthorities();
    }

    public static String getCurrentMemberEmail() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No info in security context");
        }
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);
    }

    public static Object getCurrentCredentials() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No info in security context");
        }
        return authentication.getCredentials();
    }
}
