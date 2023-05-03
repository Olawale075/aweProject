/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.security.service;

import com.codeverse.gnotify.user.domain.AppUser;
import com.codeverse.gnotify.user.domain.AppUserRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AppUserRepositoryWrapper appUserRepositoryWrapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.appUserRepositoryWrapper.findByUsernameOrEmailOrMobile(username, username, username);
        return appUser;
    }

}
