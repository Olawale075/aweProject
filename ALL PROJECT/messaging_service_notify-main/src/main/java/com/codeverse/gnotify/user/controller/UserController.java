/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.controller;

import com.codeverse.gnotify.general.data.ApiConstants;
import static com.codeverse.gnotify.general.data.ApiConstants.USER_BASE_URL;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.user.data.AppUserData;
import com.codeverse.gnotify.user.domain.AppUser;
import com.codeverse.gnotify.user.service.AppUserReadService;
import com.codeverse.gnotify.user.service.AppUserWriteService;
import com.codeverse.gnotify.user.service.ChangePasswordData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle.Thompson
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = USER_BASE_URL)
public class UserController {

    private final AppUserReadService appUserReadService;
    private final AppUserWriteService appUserWriteService;

    @Autowired
    public UserController(final AppUserReadService appUserReadService,
            final AppUserWriteService appUserWriteService) {
        this.appUserReadService = appUserReadService;
        this.appUserWriteService = appUserWriteService;
    }

    @PostMapping(
            value = "",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> createUser(@RequestBody @Valid AppUserData appUserData) {
        final ApiResponseMessage apiResponseMessage = this.appUserWriteService.createAppUser(appUserData);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.CREATED);
    }

    //allow user who are logged in to change password
    @PutMapping(
            value = "change-password/{id}/active",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> changePasswordAppUser(@RequestBody @Valid ChangePasswordData changePasswordData, @PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.appUserWriteService.changePasswordAppUser(changePasswordData, id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @PutMapping(
            value = "/{id}",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> updateAppUser(@RequestBody AppUserData appUserData, @PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.appUserWriteService.updateAppUser(appUserData, id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> deleteAppUser(@PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.appUserWriteService.deleteAppUser(id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    private boolean is(final String commandParam, final String commandValue) {
        return StringUtils.isNotBlank(commandParam) && commandParam.trim().equalsIgnoreCase(commandValue);
    }

    @Operation(summary = "Enable|Disable a AppUser")
    @PostMapping(
            value = "/{id}",
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> actionsOnAppUsers(@PathVariable(value = "id") Long id,
            @Parameter(description = "use command disable|enable")
            @RequestParam(value = "command") String command) {
        ApiResponseMessage apiResponseMessage = null;
        if (is(command, "disable")) {
            apiResponseMessage = this.appUserWriteService.disableAppUser(id);
        } else if (is(command, "enable")) {
            apiResponseMessage = this.appUserWriteService.enableAppUser(id);
        } else {
            throw new GeneralPlatformDomainRuleException("command", command + " is not supported.",
                    new Object[]{"enable", "disable"});
        }
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveOneAppUser(@PathVariable(value = "id") Long id) {
        final AppUser appUser = this.appUserReadService.retrieveAppUser(id);
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveAppUser(
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
        @SortDefault(sort = "id", direction = Sort.Direction.DESC)
    }) @ParameterObject Pageable pageable,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "username", required = false) String username
    ) {
        final ApiResponseMessage apiResponseMessage = this.appUserReadService.retrieveAppUser(email, mobile, username, pageable);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @PostMapping(value = "/logout", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> logoutAppUser() {
        final ApiResponseMessage apiResponseMessage = this.appUserReadService.logoutAppUser();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

}
