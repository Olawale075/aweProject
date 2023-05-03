package com.codeverse.gnotify.notification.service;

import com.codeverse.gnotify.general.data.EnumOptionData;
import com.codeverse.gnotify.notification.domain.NotificationType;
import java.util.List;

public interface NotificationTypeReadService {

    List<EnumOptionData> getNotificationTypeTemplate();

    List<NotificationType> retrieveNotificationType();

    NotificationType retrieveNotificationType(final Long id);

}
