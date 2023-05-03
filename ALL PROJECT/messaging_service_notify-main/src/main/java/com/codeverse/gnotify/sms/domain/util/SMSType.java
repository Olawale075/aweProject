/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.domain.util;

/**
 *
 * @author Olakunle.Thompson
 */
public enum SMSType {
    FUND_TRANSFER("FUND_TRANSFER", 1),
    CHEQUE_STATUS("CHEQUE_STATUS", 2), //ATM_STATUS("ATM_STATUS", 3),
    //BALANCE_NOTIFICATION("BALANCE_NOTIFICATION", 4)
    ;

    private final String code;
    private final Integer value;

    private SMSType(String code, Integer value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public Integer getValue() {
        return value;
    }

}
