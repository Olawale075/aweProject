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
package com.codeverse.gnotify.user.data;

import com.codeverse.gnotify.user.domain.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class AppUserData implements Serializable {

    private Long id;

    @Email(message = "Email is required")
    private String email;

    private String mobile;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "First name is required")
    private String firstname;
    private String displayName;

    @NotBlank(message = "Last name is required")
    private String lastname;

    private boolean enabled;

    private boolean deleted;

    private Set<Role> roles;

    private String token;

    Collection<GrantedAuthority> authorities;
    private Set<Long> roleId = new HashSet<>();

    public AppUserData(Long id, String email, String mobile, String username, String displayName, String firstname, String lastname, boolean enabled, boolean deleted, Set<Role> roles, String token, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.username = username;
        this.firstname = firstname;
        this.displayName = displayName;
        this.lastname = lastname;
        this.enabled = enabled;
        this.deleted = deleted;
        this.roles = roles;
        this.token = token;
        this.authorities = authorities;
    }

}
