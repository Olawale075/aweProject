/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.domain.util;

/**
 *
 * @author Olakunle.Thompson
 */
public enum FileSeparator {
    CARET("CARET", "^"),
    PIPE("PIPE", "|"),
    COMMA("COMMA", ",");

    private final String code;
    private final String value;

    private FileSeparator(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
