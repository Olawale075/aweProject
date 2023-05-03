/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.security.service.JwtUtils;
import com.codeverse.gnotify.user.domain.AppUser;
import com.codeverse.gnotify.user.domain.AppUserRepositoryWrapper;
import com.codeverse.gnotify.user.exception.AppUserDeletedException;
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
public class AppUserReadServiceImpl implements AppUserReadService {

    private final AppUserRepositoryWrapper appUserRepositoryWrapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public AppUserReadServiceImpl(
            final AppUserRepositoryWrapper appUserRepositoryWrapper, final JwtUtils jwtUtils) {
        this.appUserRepositoryWrapper = appUserRepositoryWrapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ApiResponseMessage retrieveAppUser(final String email, final String mobile, final String username, Pageable pageable) {
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        String message = "AppUsers Available";
        Integer status;
        try {

            Page<AppUser> appUsers = this.appUserRepositoryWrapper.findAll(retrieveAppUserSpecification(email, mobile, username), pageable);
            if (appUsers.isEmpty()) {
                message = "No AppUsers Available";
                status = HttpStatus.NO_CONTENT.value();
            } else {
                apiResponseMessage.setData((Serializable) appUsers);
                status = HttpStatus.OK.value();
            }
        } catch (Exception ex) {
            log.error("AppUsers Error: {}", ex);
            message = ex.getMessage();
            status = HttpStatus.SERVICE_UNAVAILABLE.value();
        }
        apiResponseMessage.setMessage(message);
        apiResponseMessage.setStatus(status);
        return apiResponseMessage;
    }

    public Specification<AppUser> retrieveAppUserSpecification(final String email, final String mobile, final String username) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(email)) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }
            if (StringUtils.isNotBlank(mobile)) {
                predicates.add(criteriaBuilder.equal(root.get("mobile"), mobile));
            }
            if (StringUtils.isNotBlank(username)) {
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
            }
            predicates.add(criteriaBuilder.isFalse(root.get("deleted")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public AppUser retrieveAppUser(Long id) {
        final AppUser appUser = this.appUserRepositoryWrapper.findOneWithNotFoundDetection(id);
        if (appUser.isDeleted()) {
            throw new AppUserDeletedException(id);
        }
        final AppUser appUserFinal = new AppUser(appUser.getUsername(), appUser.getMobile(),
                appUser.getEmail(), appUser.getFirstname(), appUser.getLastname(), appUser.getRoles(), appUser.isEnabled());
        return appUserFinal;
    }

    @Override
    public ApiResponseMessage logoutAppUser() {
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        String message = "You are logged out.";
        Integer status = HttpStatus.OK.value();
        try {
            message = "not yet implemented";
        } catch (Exception ex) {
            log.error("AppUsers Error: {}", ex);
            message = ex.getMessage();
            status = HttpStatus.BAD_REQUEST.value();
        }
        apiResponseMessage.setMessage(message);
        apiResponseMessage.setStatus(status);
        return apiResponseMessage;
    }

}
