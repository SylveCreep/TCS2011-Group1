package com.example.server.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionUtils {
    public static UserDetails getPrincipal() {
        if (SecurityContextHolder.getContext() != null &&
                SecurityContextHolder.getContext().getAuthentication() != null) {
            return (UserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
        }
        return null;
    }

    public static String getEmail(){
        UserDetails user = getPrincipal();
        if(user != null){
            return user.getUsername();
        }
        return null;
    }
}


