/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.notification.domain;

/**
 *
 * @author Olakunle.Thompson
 */
public enum NotificationTemplate {
    Email("Email"),
    Sms("Sms");

    private final String code;

    private NotificationTemplate(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
