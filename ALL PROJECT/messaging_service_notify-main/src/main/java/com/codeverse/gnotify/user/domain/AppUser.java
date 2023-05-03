/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.codeverse.gnotify.user.domain;

import com.codeverse.gnotify.general.data.DateUtils;
import com.codeverse.gnotify.general.domain.AbstractPersistableCustom;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.user.service.RandomPasswordGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Entity
@Table(name = "m_appuser", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"}, name = "username_org"),
    @UniqueConstraint(columnNames = {"mobile"}, name = "mobile_org"),
    @UniqueConstraint(columnNames = {"email"}, name = "email_org")
}
)
public class AppUser extends AbstractPersistableCustom<Long> implements PlatformUser {

    private static final Logger LOG = LoggerFactory.getLogger(AppUser.class);

    @Email(message = "Provide a valid email address")
    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Column(name = "mobile", nullable = true, length = 100)
    private String mobile;

    @NotBlank
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "firstname", nullable = false, length = 100)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nonexpired", nullable = false)
    private Boolean accountNonExpired;

    @Column(name = "nonlocked", nullable = false)
    private Boolean accountNonLocked;

    @Column(name = "nonexpired_credentials", nullable = false)
    private Boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m_appuser_role", joinColumns = @JoinColumn(name = "appuser_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_time_password_updated")
    private LocalDateTime lastTimePasswordUpdated;

    @Column(name = "password_never_expires", nullable = false)
    private Boolean passwordNeverExpires;

    @Transient
    private String token;

    public static AppUser instanceAppUser(final String username, final String mobile, final String email, final String firstname, final String lastname,
            final Set<Role> allRoles, final String token, final boolean enabled) {
        return new AppUser(username, mobile, email, firstname, lastname,
                allRoles, token, enabled);
    }

    public AppUser(String username, String mobile, String email, String firstname, String lastname,
            Set<Role> roles, boolean enabled) {
        this.passwordNeverExpires = null;
        this.accountNonExpired = null;
        this.accountNonLocked = null;
        this.credentialsNonExpired = null;
        this.enabled = enabled;
        this.email = email;
        this.mobile = mobile;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
    }

    private AppUser(String username, String mobile, String email, String firstname, String lastname,
            Set<Role> roles, String token, boolean enabled) {
        this.passwordNeverExpires = null;
        this.accountNonExpired = null;
        this.accountNonLocked = null;
        this.credentialsNonExpired = null;
        this.enabled = enabled;
        this.email = email;
        this.mobile = mobile;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
        this.token = token;
    }

    public static AppUser fromJson(final boolean sendMobile, final boolean sendEmail,
            final String username, final String email, final String firstname, final String lastname,
            final Set<Role> allRoles, final PasswordEncoder encoder) {

        //using default pawword for the main time
        final String passwordGenerated = "gnotify";//new RandomPasswordGenerator(13).generate();
        final String password = getEncodedPassword(passwordGenerated, encoder);

        if (sendMobile) {
            //send to Mobile
        }
        if (sendEmail) {
            //send to Email
        }

        boolean passwordNeverExpire = false;

        final boolean userEnabled = true;
        final boolean userAccountNonExpired = true;
        final boolean userCredentialsNonExpired = true;
        final boolean userAccountNonLocked = true;

        final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("DUMMY_ROLE_NOT_USED_OR_PERSISTED_TO_AVOID_EXCEPTION"));

        final User user = new User(username, password, userEnabled, userAccountNonExpired, userCredentialsNonExpired, userAccountNonLocked,
                authorities);

        return new AppUser(user, allRoles, email, firstname, lastname, passwordNeverExpire);
    }

    protected AppUser() {
        this.accountNonLocked = false;
        this.credentialsNonExpired = false;
        this.roles = new HashSet<>();
    }

    public AppUser(final User user, final Set<Role> roles, final String email, final String firstname,
            final String lastname, final boolean passwordNeverExpire) {
        this.email = StringUtils.isNotBlank(email) ? email.trim() : null;
        this.username = user.getUsername().trim();
        this.firstname = firstname.trim();
        this.lastname = lastname.trim();
        this.password = user.getPassword().trim();
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.enabled = user.isEnabled();
        this.roles = roles;
        this.lastTimePasswordUpdated = DateUtils.getLocalDateTimeOfTenant();
        this.passwordNeverExpires = passwordNeverExpire;
    }

    public void updatePassword(final String encodePassword) {
        this.password = encodePassword;
        this.lastTimePasswordUpdated = DateUtils.getLocalDateTimeOfTenant();
    }

    public void updateRoles(final Set<Role> allRoles) {
        if (!allRoles.isEmpty()) {
            this.roles.clear();
            this.roles = allRoles;
        }
    }

    /*public Map<String, Object> update(final JsonCommand command, final PlatformPasswordEncoder platformPasswordEncoder,
            final Collection<Client> clients) {

        final Map<String, Object> actualChanges = new LinkedHashMap<>(7);

        // unencoded password provided
        final String passwordParamName = "password";
        final String passwordEncodedParamName = "passwordEncoded";
        if (command.hasParameter(passwordParamName)) {
            if (command.isChangeInPasswordParameterNamed(passwordParamName, this.password, platformPasswordEncoder, getId())) {
                final String passwordEncodedValue = command.passwordValueOfParameterNamed(passwordParamName, platformPasswordEncoder,
                        getId());
                actualChanges.put(passwordEncodedParamName, passwordEncodedValue);
                updatePassword(passwordEncodedValue);
            }
        }

        if (command.hasParameter(passwordEncodedParamName)) {
            if (command.isChangeInStringParameterNamed(passwordEncodedParamName, this.password)) {
                final String newValue = command.stringValueOfParameterNamed(passwordEncodedParamName);
                actualChanges.put(passwordEncodedParamName, newValue);
                updatePassword(newValue);
            }
        }

        final String officeIdParamName = "officeId";
        if (command.isChangeInLongParameterNamed(officeIdParamName, this.office.getId())) {
            final Long newValue = command.longValueOfParameterNamed(officeIdParamName);
            actualChanges.put(officeIdParamName, newValue);
        }

        final String staffIdParamName = "staffId";
        if (command.hasParameter(staffIdParamName)
                && (this.staff == null || command.isChangeInLongParameterNamed(staffIdParamName, this.staff.getId()))) {
            final Long newValue = command.longValueOfParameterNamed(staffIdParamName);
            actualChanges.put(staffIdParamName, newValue);
        }

        final String rolesParamName = "roles";
        if (command.isChangeInArrayParameterNamed(rolesParamName, getRolesAsIdStringArray())) {
            final String[] newValue = command.arrayValueOfParameterNamed(rolesParamName);
            actualChanges.put(rolesParamName, newValue);
        }

        final String usernameParamName = "username";
        if (command.isChangeInStringParameterNamed(usernameParamName, this.username)) {

            // TODO Remove this check once we are identifying system user based on user ID
            if (isSystemUser()) {
                throw new NoAuthorizationException("User name of current system user may not be modified");
            }

            final String newValue = command.stringValueOfParameterNamed(usernameParamName);
            actualChanges.put(usernameParamName, newValue);
            this.username = newValue;
        }

        final String firstnameParamName = "firstname";
        if (command.isChangeInStringParameterNamed(firstnameParamName, this.firstname)) {
            final String newValue = command.stringValueOfParameterNamed(firstnameParamName);
            actualChanges.put(firstnameParamName, newValue);
            this.firstname = newValue;
        }

        final String lastnameParamName = "lastname";
        if (command.isChangeInStringParameterNamed(lastnameParamName, this.lastname)) {
            final String newValue = command.stringValueOfParameterNamed(lastnameParamName);
            actualChanges.put(lastnameParamName, newValue);
            this.lastname = newValue;
        }

        final String emailParamName = "email";
        if (command.isChangeInStringParameterNamed(emailParamName, this.email)) {
            final String newValue = command.stringValueOfParameterNamed(emailParamName);
            actualChanges.put(emailParamName, newValue);
            this.email = newValue;
        }

        final String passwordNeverExpire = "passwordNeverExpires";

        if (command.hasParameter(passwordNeverExpire)) {
            if (command.isChangeInBooleanParameterNamed(passwordNeverExpire, this.passwordNeverExpires)) {
                final boolean newValue = command.booleanPrimitiveValueOfParameterNamed(passwordNeverExpire);
                actualChanges.put(passwordNeverExpire, newValue);
                this.passwordNeverExpires = newValue;
            }
        }

        if (command.hasParameter(AppUserConstants.IS_SELF_SERVICE_USER)) {
            if (command.isChangeInBooleanParameterNamed(AppUserConstants.IS_SELF_SERVICE_USER, this.isSelfServiceUser)) {
                final boolean newValue = command.booleanPrimitiveValueOfParameterNamed(AppUserConstants.IS_SELF_SERVICE_USER);
                actualChanges.put(AppUserConstants.IS_SELF_SERVICE_USER, newValue);
                this.isSelfServiceUser = newValue;
            }
        }

        if (this.isSelfServiceUser && command.hasParameter(AppUserConstants.CLIENTS)) {
            actualChanges.put(AppUserConstants.CLIENTS, command.arrayValueOfParameterNamed(AppUserConstants.CLIENTS));
            Set<AppUserClientMapping> newClients = createAppUserClientMappings(clients);
            if (this.appUserClientMappings == null) {
                this.appUserClientMappings = new HashSet<>();
            } else {
                this.appUserClientMappings.retainAll(newClients);
            }
            this.appUserClientMappings.addAll(newClients);
        } else if (!this.isSelfServiceUser && actualChanges.containsKey(AppUserConstants.IS_SELF_SERVICE_USER)) {
            actualChanges.put(AppUserConstants.CLIENTS, new ArrayList<>());
            if (this.appUserClientMappings != null) {
                this.appUserClientMappings.clear();
            }
        }

        return actualChanges;
    }*/
    private String[] getRolesAsIdStringArray() {
        final List<String> roleIds = new ArrayList<>();

        this.roles.forEach(role -> {
            roleIds.add(role.getId().toString());
        });

        return roleIds.toArray(new String[roleIds.size()]);
    }

    /**
     * Delete is a <i>soft delete</i>. Updates flag so it wont appear in
     * query/report results.
     *
     * Any fields with unique constraints and prepended with id of record.
     */
    public void delete() {
        /*if (isSystemUser()) {
            throw new NoAuthorizationException("User configured as the system user cannot be deleted");
        }*/

        this.deleted = true;
        this.enabled = false;
        this.accountNonExpired = false;
        this.username = getId() + "_DELETED_" + this.username;
        this.roles.clear();
    }

    public void EnableDisableUser(boolean enableUser) {
        this.enabled = enableUser;
        this.accountNonExpired = enableUser;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    /*public boolean isSystemUser() {
        // TODO Determine system user by ID not by user name
        if (this.username.equals(AppUserConstants.SYSTEM_USER_NAME)) {
            return true;
        }

        return false;
    }*/
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return populateGrantedAuthorities();
    }

    private List<GrantedAuthority> populateGrantedAuthorities() {
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(this.roles)) {
            this.roles.stream().map(role -> role.getPermissions()).forEachOrdered(permissions -> {
                permissions.forEach(permission -> {
                    grantedAuthorities.add(new SimpleGrantedAuthority(permission.getCode()));
                });
            });

        }
        return grantedAuthorities;
    }

    public String getDisplayName() {
        String firstName = StringUtils.isNotBlank(this.firstname) ? this.firstname : "";
        if (StringUtils.isNotBlank(this.lastname)) {
            return firstName + " " + this.lastname;
        }
        return firstName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public static String getEncodedPassword(final String password, final PasswordEncoder encoder) {
        // LOG.info("getEncodedPassword: {}", command.toString());
        if (StringUtils.isBlank(password)) {
            throw new GeneralPlatformDomainRuleException("error.password", "Password is not available");
        }
        String passwordEncodedValue = encoder.encode(password);

        return passwordEncodedValue;
    }

    public boolean isNotEnabled() {
        return !isEnabled();
    }

    public void updatePassword(final String password, final PasswordEncoder encoder) {
        this.updatePassword(getEncodedPassword(password, encoder));
    }
}
