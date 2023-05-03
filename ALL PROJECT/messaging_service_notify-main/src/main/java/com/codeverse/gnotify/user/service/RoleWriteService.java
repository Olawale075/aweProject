package com.codeverse.gnotify.user.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.user.domain.Role;

public interface RoleWriteService {

    ApiResponseMessage deleteRole(final Long id);

    ApiResponseMessage createRole(Role role);

    ApiResponseMessage updateRole(Role role, Long id);

    ApiResponseMessage disableRole(Long id);

    ApiResponseMessage enableRole(Long id);
}
