/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.notification.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.notification.domain.NotificationType;
import com.codeverse.gnotify.notification.domain.NotificationTypeRepositoryWrapper;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
public class NotificationTypeWriteServiceImpl implements NotificationTypeWriteService {

    private final NotificationTypeRepositoryWrapper notificationTypeRepositoryWrapper;

    @Autowired
    public NotificationTypeWriteServiceImpl(NotificationTypeRepositoryWrapper notificationTypeRepositoryWrapper) {
        this.notificationTypeRepositoryWrapper = notificationTypeRepositoryWrapper;
    }

    @Transactional
    @Override
    public ApiResponseMessage updateNotificationType(NotificationType notificationType, Long id) {
        final NotificationType notificationTypeToUpdate = this.notificationTypeRepositoryWrapper.findOneWithNotFoundDetection(id);

        try {
            notificationTypeToUpdate.setNotificationTypeConfiguration(notificationType.getNotificationTypeConfiguration());

            notificationTypeToUpdate.setLastModifiedDate(new Date());
            this.notificationTypeRepositoryWrapper.saveAndFlush(notificationTypeToUpdate);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.update.notification.type", e.getMessage(), "Update integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Notification Type updated", notificationType, null);
    }

    @Transactional
    @Override
    public ApiResponseMessage createNotificationType(NotificationType notificationType) {
        try {
            notificationType.setCreatedDate(new Date());
            this.notificationTypeRepositoryWrapper.saveAndFlush(notificationType);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.create.notification.type", e.getMessage(), "Create integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.CREATED.value(), "SNotification Type created", notificationType, null);
    }

    @Transactional
    @Override
    public ApiResponseMessage deleteNotificationType(final Long id) {
        final NotificationType notificationType = this.notificationTypeRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            this.notificationTypeRepositoryWrapper.delete(notificationType);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.delete.notification.type", e.getMessage(), "Delete integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "SNotification Type deleted", id, null);
    }

}
