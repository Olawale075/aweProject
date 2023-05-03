/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage.listener;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;

/**
 *
 * @author Olakunle.Thompson
 */
@Slf4j
public class SMSMessageItemProcessListener implements ItemProcessListener<SMSMessage, SMSMessage> {

    @Override
    public void beforeProcess(SMSMessage creditCard) {
        log.info("beforeProcess");
    }

    @Override
    public void afterProcess(SMSMessage smsMessageFirst, SMSMessage smsMessageSecond) {
        log.info("smsMessageFirst: ---> " + smsMessageFirst + " \n smsMessageSecond: ---> " + smsMessageSecond);
    }

    @Override
    public void onProcessError(SMSMessage smsMessage, Exception e) {
        log.info("smsMessage onProcessError");
    }

}
