package com.codeverse.gnotify.user.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.user.domain.AppUser;
import org.springframework.data.domain.Pageable;

public interface AppUserReadService {

    AppUser retrieveAppUser(Long id);

    ApiResponseMessage retrieveAppUser(final String email, final String mobile, final String username, Pageable pageable);

    ApiResponseMessage logoutAppUser();
}
