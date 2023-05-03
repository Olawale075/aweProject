/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.security.service;

import com.codeverse.gnotify.security.exception.UnAuthenticatedUserException;
import com.codeverse.gnotify.user.domain.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
@Slf4j
public class SpringSecurityPlatformSecurityContext implements PlatformSecurityContext {

    @Override
    public AppUser authenticatedUser() {

        AppUser currentUser = null;
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            final Authentication auth = context.getAuthentication();
            if (auth != null) {
                try {
                    currentUser = (AppUser) auth.getPrincipal();
                } catch (Exception e) {
                    log.error(
                            "error encountered while trying to get User details " + e.getLocalizedMessage());
                }
            }
        }

        if (currentUser == null) {
            throw new UnAuthenticatedUserException("error.msg.invalid.User", "User not authenticated");
        }

        return currentUser;
    }

}
