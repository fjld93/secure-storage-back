package com.fjld.secure_storage.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
	
	public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder
        		.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }
	
	public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder
        		.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails;
        }
        return null;
    }

}
