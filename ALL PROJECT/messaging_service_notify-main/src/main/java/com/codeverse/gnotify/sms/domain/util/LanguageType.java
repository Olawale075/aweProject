/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.domain.util;

/**
 *
 * @author Olakunle.Thompson
 */
public enum LanguageType {
    en("en", "English"),
    fr("fr", "French");

    private final String code;
    private final String description;

    private LanguageType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
