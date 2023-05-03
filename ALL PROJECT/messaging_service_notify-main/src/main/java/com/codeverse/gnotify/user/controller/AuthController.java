/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.controller;

import com.codeverse.gnotify.general.data.ApiConstants;
import static com.codeverse.gnotify.general.data.ApiConstants.AUTH_BASE_URL;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.user.data.AuthRequest;
import com.codeverse.gnotify.user.service.AppUserWriteService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle.Thompson
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = AUTH_BASE_URL)
public class AuthController {

    private final AppUserWriteService appUserWriteService;
    
    @Autowired
    public AuthController(final AppUserWriteService appUserWriteService) {
        this.appUserWriteService = appUserWriteService;
    }

    @PostMapping(
            value = "",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> createVariableTemplate(@Valid @RequestBody AuthRequest authRequest) {
        final ApiResponseMessage apiResponseMessage = this.appUserWriteService.authAppUser(authRequest);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.CREATED);
    }

}
