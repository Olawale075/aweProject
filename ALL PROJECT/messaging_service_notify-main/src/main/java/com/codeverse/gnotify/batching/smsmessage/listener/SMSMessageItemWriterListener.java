/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage.listener;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

/**
 *
 * @author Olakunle.Thompson
 */
@Slf4j
public class SMSMessageItemWriterListener implements ItemWriteListener<SMSMessage> {


    @Override
    public void beforeWrite(List<? extends SMSMessage> list) {
        log.info("beforeWrite");
    }


    @Override
    public void afterWrite(List<? extends SMSMessage> list) {
        list.forEach(smsMessage -> {
            log.info("smsMessage afterWrite :" + smsMessage.toString());
        });
    }

    @Override
    public void onWriteError(Exception e, List<? extends SMSMessage> list) {
        log.info("smsMessage onWriteError");
    }
    
}
