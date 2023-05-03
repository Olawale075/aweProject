package com.codeverse.gnotify.user.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.user.data.AppUserData;
import com.codeverse.gnotify.user.data.AuthRequest;

public interface AppUserWriteService {

    ApiResponseMessage authAppUser(final AuthRequest authRequest);

    ApiResponseMessage deleteAppUser(final Long id);

    ApiResponseMessage changePasswordAppUser(final ChangePasswordData changePasswordData, final Long id);

    ApiResponseMessage createAppUser(AppUserData appUserData);

    ApiResponseMessage updateAppUser(AppUserData appUserData, Long id);

    ApiResponseMessage disableAppUser(Long id);

    ApiResponseMessage enableAppUser(Long id);
}
