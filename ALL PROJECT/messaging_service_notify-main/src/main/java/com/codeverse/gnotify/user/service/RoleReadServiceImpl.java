/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.user.domain.Permission;
import com.codeverse.gnotify.user.domain.PermissionRepository;
import com.codeverse.gnotify.user.domain.Role;
import com.codeverse.gnotify.user.domain.RoleRepositoryWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
@Slf4j
public class RoleReadServiceImpl implements RoleReadService {

    private final PermissionRepository permissionRepository;
    private final RoleRepositoryWrapper roleRepositoryWrapper;

    @Autowired
    public RoleReadServiceImpl(final PermissionRepository permissionRepository,
            final RoleRepositoryWrapper roleRepositoryWrapper) {
        this.permissionRepository = permissionRepository;
        this.roleRepositoryWrapper = roleRepositoryWrapper;
    }

    @Override
    public List<Permission> getPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public ApiResponseMessage retrieveRole(final String name, Pageable pageable) {
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        String message = "Roles Available";
        Integer status;
        try {

            Page<Role> roles = this.roleRepositoryWrapper.findAll(retrieveRoleSpecification(name), pageable);
            if (roles.isEmpty()) {
                message = "No Roles Available";
                status = HttpStatus.NO_CONTENT.value();
            } else {
                apiResponseMessage.setData((Serializable) roles);
                status = HttpStatus.OK.value();
            }
        } catch (Exception ex) {
            log.error("Roles Error: {}", ex);
            message = ex.getMessage();
            status = HttpStatus.SERVICE_UNAVAILABLE.value();
        }
        apiResponseMessage.setMessage(message);
        apiResponseMessage.setStatus(status);
        return apiResponseMessage;
    }

    public Specification<Role> retrieveRoleSpecification(final String name) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(name)) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Role retrieveRole(Long id) {
        return this.roleRepositoryWrapper.findOneWithNotFoundDetection(id);
    }

}
