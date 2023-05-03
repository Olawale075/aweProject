/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.notification.domain;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Olakunle.Thompson
 */
@Converter(autoApply = true)
public class NotificationTemplateConverter implements AttributeConverter<NotificationTemplate, String> {

    @Override
    public String convertToDatabaseColumn(NotificationTemplate notificationType) {
        if (notificationType == null) {
            return null;
        }
        return notificationType.getCode();
    }

    @Override
    public NotificationTemplate convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(NotificationTemplate.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
