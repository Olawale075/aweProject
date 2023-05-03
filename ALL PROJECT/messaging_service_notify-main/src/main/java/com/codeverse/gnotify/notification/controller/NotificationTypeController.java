/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.notification.controller;

import com.codeverse.gnotify.general.data.ApiConstants;
import static com.codeverse.gnotify.general.data.ApiConstants.NOTIFICATION_TYPE_BASE_URL;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.notification.domain.NotificationType;
import com.codeverse.gnotify.notification.service.NotificationTypeReadService;
import com.codeverse.gnotify.notification.service.NotificationTypeWriteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle.Thompson
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = NOTIFICATION_TYPE_BASE_URL)
public class NotificationTypeController {

    private final NotificationTypeReadService notificationTypeReadService;
    private final NotificationTypeWriteService notificationTypeWriteService;

    @Autowired
    public NotificationTypeController(final NotificationTypeReadService notificationTypeReadService,
            final NotificationTypeWriteService notificationTypeWriteService) {
        this.notificationTypeWriteService = notificationTypeWriteService;
        this.notificationTypeReadService = notificationTypeReadService;
    }

    @GetMapping(value = "", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveNotificationType() {
        List<NotificationType> variableTemplates = this.notificationTypeReadService.retrieveNotificationType();
        return new ResponseEntity<>(variableTemplates, HttpStatus.OK);
    }

    @PostMapping(
            value = "",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> createNotificationType(@RequestBody NotificationType notificationType) {
        final ApiResponseMessage apiResponseMessage = this.notificationTypeWriteService.createNotificationType(notificationType);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> updateNotificationType(@RequestBody NotificationType notificationType, @PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.notificationTypeWriteService.updateNotificationType(notificationType, id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> deleteNotificationType(@PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.notificationTypeWriteService.deleteNotificationType(id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }
}
