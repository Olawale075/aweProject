/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.user.domain.Permission;
import com.codeverse.gnotify.user.domain.PermissionRepositoryWrapper;
import com.codeverse.gnotify.user.domain.Role;
import com.codeverse.gnotify.user.domain.RoleRepositoryWrapper;
import com.codeverse.gnotify.user.exception.PermissionNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
@Slf4j
public class RoleWriteServiceImpl implements RoleWriteService {

    private final RoleRepositoryWrapper roleRepositoryWrapper;
    private final PermissionRepositoryWrapper permissionRepositoryWrapper;

    @Autowired
    public RoleWriteServiceImpl(RoleRepositoryWrapper roleRepositoryWrapper, final PermissionRepositoryWrapper permissionRepositoryWrapper) {
        this.roleRepositoryWrapper = roleRepositoryWrapper;
        this.permissionRepositoryWrapper = permissionRepositoryWrapper;
    }

    @Transactional
    @Override
    public ApiResponseMessage updateRole(Role role, Long id) {
        final Role roleToUpdate = this.roleRepositoryWrapper.findOneWithNotFoundDetection(id);

        Boolean[] getDisabled = {BooleanUtils.isTrue(role.getDisabled()), BooleanUtils.isFalse(role.getDisabled())};
        if (BooleanUtils.or(getDisabled)) {
            throw new GeneralPlatformDomainRuleException("error.update.role", "Activate/De-activate is not allowed on role update.", "Update integrity issue");
        }
        try {
            if (StringUtils.isNotBlank(role.getName())
                    && !StringUtils.equals(roleToUpdate.getName(), role.getName())) {
                roleToUpdate.setName(role.getName());
            }
            if (StringUtils.isNotBlank(role.getDescription())
                    && !StringUtils.equals(roleToUpdate.getDescription(), role.getDescription())) {
                roleToUpdate.setDescription(role.getDescription());
            }
            roleToUpdate.setPermissions(setFinalPermissions(role));

            this.roleRepositoryWrapper.saveAndFlush(roleToUpdate);
        } catch (GeneralPlatformDomainRuleException e) {
            throw new GeneralPlatformDomainRuleException("error.update.role", e.getMessage(), "Update integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Role updated", role, null);
    }

    @Transactional
    @Override
    public ApiResponseMessage createRole(Role role) {
        try {
            role.setDisabled(Boolean.FALSE);
            role.setPermissions(setFinalPermissions(role));

            this.roleRepositoryWrapper.saveAndFlush(role);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.create.role", e.getMessage(), "Create integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.CREATED.value(), "Role created", role, null);
    }

    private Set<Permission> setFinalPermissions(Role role) {
        final Set<Permission> finalPermissions = new HashSet<>();
        if (!CollectionUtils.isEmpty(role.getPermissionsId())) {
            final Set<Long> permissions = role.getPermissionsId();
            //log.info("permissions: {}", Arrays.toString(permissions.toArray()));
            final Collection<Permission> allPermissions = this.permissionRepositoryWrapper.findAll();

            permissions.forEach(permission -> {
                //log.info("permission: {}", permission.toString());
                final Permission permissionCheck = findPermissionByCode(allPermissions, permission);
                //log.info("permissionCheck: {}", permissionCheck.toString());
                finalPermissions.add(permissionCheck);
            });
            //role.setPermissions(finalPermissions);
        }
        //log.info("finalPermissions: {}", Arrays.toString(finalPermissions.toArray()));
        return finalPermissions;
    }

    @Transactional
    @Override
    public ApiResponseMessage deleteRole(final Long id) {
        final Role role = this.roleRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            this.roleRepositoryWrapper.delete(role);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.delete.role", e.getMessage(), "Delete integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Role deleted", id, null);
    }

    private Permission findPermissionByCode(final Collection<Permission> allPermissions, final Long permissionId) {

        if (allPermissions != null) {
            for (final Permission permission : allPermissions) {
                if (permission.hasId(permissionId)) {
                    return permission;
                }
            }
        }
        throw new PermissionNotFoundException(permissionId);
    }

    @Transactional
    @Override
    public ApiResponseMessage disableRole(Long id) {
        final Role role = this.roleRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            role.disableRole();
            this.roleRepositoryWrapper.saveAndFlush(role);
        } catch (GeneralPlatformDomainRuleException e) {
            throw new GeneralPlatformDomainRuleException("error.disable.role", e.getMessage(), "Disable integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Role disabled", id, null);
    }

    @Transactional
    @Override
    public ApiResponseMessage enableRole(Long id) {
        final Role role = this.roleRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            role.enableRole();
            this.roleRepositoryWrapper.saveAndFlush(role);
        } catch (GeneralPlatformDomainRuleException e) {
            throw new GeneralPlatformDomainRuleException("error.enable.role", e.getMessage(), "Enable integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Role Enabled", id, null);
    }

}
