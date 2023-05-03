/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.security.service;

import com.codeverse.gnotify.user.domain.AppUser;

/**
 *
 * @author Olakunle.Thompson
 */
public interface PlatformSecurityContext {

    AppUser authenticatedUser();
}
