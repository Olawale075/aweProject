/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.notification.service;

import com.codeverse.gnotify.general.data.EnumOptionData;
import com.codeverse.gnotify.notification.domain.NotificationTemplate;
import com.codeverse.gnotify.notification.domain.NotificationType;
import com.codeverse.gnotify.notification.domain.NotificationTypeRepositoryWrapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
public class NotificationTypeReadServiceImpl implements NotificationTypeReadService {

    private final NotificationTypeRepositoryWrapper notificationTypeRepositoryWrapper;

    @Autowired
    public NotificationTypeReadServiceImpl(NotificationTypeRepositoryWrapper notificationTypeRepositoryWrapper) {
        this.notificationTypeRepositoryWrapper = notificationTypeRepositoryWrapper;
    }

    @Override
    public List<EnumOptionData> getNotificationTypeTemplate() {
        final List<EnumOptionData> listVariableType = new ArrayList<>();
        for (final NotificationTemplate notificationTemplate : NotificationTemplate.values()) {
            EnumOptionData enumOptionData = new EnumOptionData(notificationTemplate.getCode());
            listVariableType.add(enumOptionData);
        }
        return listVariableType;
    }

    @Override
    public List<NotificationType> retrieveNotificationType() {
        return this.notificationTypeRepositoryWrapper.findAll();
    }

    @Override
    public NotificationType retrieveNotificationType(Long id) {
        return this.notificationTypeRepositoryWrapper.findOneWithNotFoundDetection(id);
    }
}
