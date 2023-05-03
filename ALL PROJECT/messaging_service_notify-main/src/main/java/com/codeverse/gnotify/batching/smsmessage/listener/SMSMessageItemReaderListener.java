/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage.listener;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

/**
 *
 * @author Olakunle.Thompson
 */
@Slf4j
public class SMSMessageItemReaderListener implements ItemReadListener<SMSMessage> {

    @Override
    public void beforeRead() {
        log.info("SMSMessage beforeRead");
    }

    @Override
    public void afterRead(SMSMessage smsMessage) {
        log.info("SMSMessage afterRead: " + smsMessage.toString());
    }

    @Override
    public void onReadError(Exception e) {
        log.info("SMSMessage onReadError");
    }

}
