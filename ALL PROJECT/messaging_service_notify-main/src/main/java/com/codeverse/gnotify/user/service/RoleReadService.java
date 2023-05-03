package com.codeverse.gnotify.user.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.user.domain.Permission;
import com.codeverse.gnotify.user.domain.Role;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RoleReadService {

    List<Permission> getPermissions();

    ApiResponseMessage retrieveRole(final String name, Pageable pageable);

    Role retrieveRole(Long id);
}
