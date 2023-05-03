/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Olakunle.Thompson
 */
public class SMSMessageItemProcessor implements ItemProcessor<SMSMessage, SMSMessage> {

    @Override
    public SMSMessage process(SMSMessage smsMessage) throws Exception {
        return smsMessage;
    }

}
