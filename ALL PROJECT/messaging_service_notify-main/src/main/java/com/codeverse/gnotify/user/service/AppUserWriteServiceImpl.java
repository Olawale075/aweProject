/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.security.service.JwtUtils;
import com.codeverse.gnotify.user.data.AppUserData;
import com.codeverse.gnotify.user.data.AuthRequest;
import com.codeverse.gnotify.user.domain.AppUser;
import com.codeverse.gnotify.user.domain.AppUserRepositoryWrapper;
import com.codeverse.gnotify.user.domain.Permission;
import com.codeverse.gnotify.user.domain.PermissionRepositoryWrapper;
import com.codeverse.gnotify.user.domain.Role;
import com.codeverse.gnotify.user.domain.RoleRepositoryWrapper;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
@Slf4j
public class AppUserWriteServiceImpl implements AppUserWriteService {

    private final RoleRepositoryWrapper roleRepositoryWrapper;
    private final PermissionRepositoryWrapper permissionRepositoryWrapper;
    private final AppUserRepositoryWrapper appUserRepositoryWrapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    @Autowired
    public AppUserWriteServiceImpl(final JwtUtils jwtUtils, final AuthenticationManager authenticationManager,
            final AppUserRepositoryWrapper appUserRepositoryWrapper,
            RoleRepositoryWrapper roleRepositoryWrapper, final PermissionRepositoryWrapper permissionRepositoryWrapper,
            final PasswordEncoder encoder) {
        this.jwtUtils = jwtUtils;
        this.appUserRepositoryWrapper = appUserRepositoryWrapper;
        this.authenticationManager = authenticationManager;
        this.roleRepositoryWrapper = roleRepositoryWrapper;
        this.permissionRepositoryWrapper = permissionRepositoryWrapper;
        this.encoder = encoder;
    }

    @Override
    public ApiResponseMessage authAppUser(final AuthRequest authRequest) {
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        if (authentication.isAuthenticated()) {
            final AppUser principal = (AppUser) authentication.getPrincipal();

            final AppUserData finalPrincipal = new AppUserData(principal.getId(), principal.getEmail(), principal.getMobile(),
                    principal.getUsername(), principal.getDisplayName(), principal.getFirstname(), principal.getLastname(), principal.isEnabled(),
                    principal.isDeleted(), null, jwt, principal.getAuthorities());

            apiResponseMessage.setData(finalPrincipal);
            apiResponseMessage.setMessage("Login successful");
            apiResponseMessage.setStatus(HttpStatus.OK.value());
        } else {
            apiResponseMessage.setMessage("Invalid username or password.");
            apiResponseMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return apiResponseMessage;
    }

    @Override
    public ApiResponseMessage deleteAppUser(Long id) {
        final AppUser appUserToDelete = this.appUserRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            //soft delete
            appUserToDelete.delete();
            this.appUserRepositoryWrapper.saveAndFlush(appUserToDelete);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.delete.user", e.getMessage(), "Delete integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "User deleted", id, null);
    }

    @Override
    public ApiResponseMessage createAppUser(AppUserData appUserData) {
        if (ObjectUtils.isEmpty(appUserData)) {
            throw new GeneralPlatformDomainRuleException("error.create.user", "User creation info cannot be empty.");
        }
        try {
            appUserData.setRoles(setFinalRoles(appUserData));

            final String username = appUserData.getEmail();
            final AppUser appUser = AppUser.fromJson(true, true, username, appUserData.getEmail(), appUserData.getFirstname(),
                    appUserData.getLastname(), appUserData.getRoles(), this.encoder);
            this.appUserRepositoryWrapper.saveAndFlush(appUser);
            return new ApiResponseMessage(HttpStatus.CREATED.value(), "User created", appUserData, null);
        } catch (Exception e) {
            log.error("createAppUser: {}", e);
            throw new GeneralPlatformDomainRuleException("error.create.user", "Record may already exits. Contact support", "Create integrity issue");
        }
    }

    @Override
    public ApiResponseMessage updateAppUser(AppUserData appUserData, Long id) {
        final AppUser appUserToUpdate = this.appUserRepositoryWrapper.findOneWithNotFoundDetection(id);

        Boolean[] getDisabled = {BooleanUtils.isTrue(appUserData.isEnabled()), BooleanUtils.isFalse(appUserData.isEnabled())};
        if (BooleanUtils.or(getDisabled)) {
            throw new GeneralPlatformDomainRuleException("error.update.user", "Activate/De-activate is not allowed on user update.", "Update integrity issue");
        }
        try {
            appUserToUpdate.setRoles(setFinalRoles(appUserData));

            if (StringUtils.isNotBlank(appUserData.getFirstname())
                    && !StringUtils.equals(appUserToUpdate.getFirstname(), appUserData.getFirstname())) {
                appUserToUpdate.setFirstname(appUserToUpdate.getFirstname());
            }
            if (StringUtils.isNotBlank(appUserData.getLastname())
                    && !StringUtils.equals(appUserToUpdate.getLastname(), appUserData.getLastname())) {
                appUserToUpdate.setLastname(appUserToUpdate.getLastname());
            }
            if (StringUtils.isNotBlank(appUserData.getEmail())
                    && !StringUtils.equals(appUserToUpdate.getEmail(), appUserData.getEmail())) {
                appUserToUpdate.setEmail(appUserToUpdate.getEmail());
                appUserToUpdate.setUsername(appUserToUpdate.getEmail());
            }

            final String username = appUserData.getEmail();
            final AppUser appUser = AppUser.fromJson(true, true, username, appUserData.getEmail(), appUserData.getFirstname(),
                    appUserData.getLastname(), appUserData.getRoles(), this.encoder);
            this.appUserRepositoryWrapper.saveAndFlush(appUser);
            return new ApiResponseMessage(HttpStatus.CREATED.value(), "User created", appUserData, null);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.create.user", e.getMessage(), "Create integrity issue");
        }
    }

    @Override
    public ApiResponseMessage disableAppUser(Long id) {
        final AppUser appUser = this.appUserRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            appUser.EnableDisableUser(false);
            this.appUserRepositoryWrapper.saveAndFlush(appUser);
        } catch (GeneralPlatformDomainRuleException e) {
            throw new GeneralPlatformDomainRuleException("error.disable.user", e.getMessage(), "Disable integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "User disabled", id, null);
    }

    @Override
    public ApiResponseMessage enableAppUser(Long id) {
        final AppUser appUser = this.appUserRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            appUser.EnableDisableUser(true);
            this.appUserRepositoryWrapper.saveAndFlush(appUser);
        } catch (GeneralPlatformDomainRuleException e) {
            throw new GeneralPlatformDomainRuleException("error.enable.user", e.getMessage(), "Enable integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "User Enabled", id, null);
    }

    private Set<Role> setFinalRoles(AppUserData appUserData) {
        if (!CollectionUtils.isEmpty(appUserData.getRoleId())) {
            final Set<Long> roles = appUserData.getRoleId();
            final Collection<Role> allRoles = this.roleRepositoryWrapper.findAll();
            return allRoles.stream().filter(predicate -> {
                return findRolesMatch(predicate, roles);
            }).collect(Collectors.toSet());
        }
        return null;
    }

    private boolean findRolesMatch(Role role, Set<Long> roleId) {
        return roleId.stream().anyMatch(action -> action.equals(role.getId()));
    }

    @Override
    public ApiResponseMessage changePasswordAppUser(ChangePasswordData changePasswordData, Long id) {
        if (!StringUtils.equals(changePasswordData.getNewPassword(), changePasswordData.getConfirmNewPassword())) {
            throw new GeneralPlatformDomainRuleException("error.change.password.active.user", "Confirmation password must match new password.", "Change Password integrity issue");
        }
        if (StringUtils.equals(changePasswordData.getNewPassword(), changePasswordData.getOldPassword())) {
            throw new GeneralPlatformDomainRuleException("error.change.password.active.user", "Old password cannot be same with new password.", "Change Password integrity issue");
        }
        final AppUser appUser = this.appUserRepositoryWrapper.findOneWithNotFoundDetection(id);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(appUser.getUsername(), changePasswordData.getOldPassword()));
        if (authentication.isAuthenticated()) {
            try {
                appUser.updatePassword(changePasswordData.getNewPassword(), encoder);
                this.appUserRepositoryWrapper.saveAndFlush(appUser);
            } catch (GeneralPlatformDomainRuleException e) {
                log.error("changePasswordAppUser: {}", e);
                throw new GeneralPlatformDomainRuleException("error.change.password.active.user", "System unable to approve password change, contact support", "Change Password integrity issue");
            }
        } else {
            throw new GeneralPlatformDomainRuleException("error.change.password.active.user", "Old password to change is not correct, contact support if error persists", "Change Password integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "User Password Changed", id, null);
    }
}
