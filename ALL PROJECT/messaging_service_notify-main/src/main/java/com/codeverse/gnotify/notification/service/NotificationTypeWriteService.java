package com.codeverse.gnotify.notification.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.notification.domain.NotificationType;

public interface NotificationTypeWriteService {

    ApiResponseMessage deleteNotificationType(final Long id);

    ApiResponseMessage createNotificationType(NotificationType notificationType);

    ApiResponseMessage updateNotificationType(NotificationType notificationType, Long id);
}
