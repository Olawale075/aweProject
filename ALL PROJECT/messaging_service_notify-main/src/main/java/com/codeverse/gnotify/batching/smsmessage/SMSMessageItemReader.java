/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.repository.SMSMessageRepositoryWrapper;
import java.util.Arrays;
import java.util.Iterator;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.StepExecution;

/**
 *
 * @author Olakunle.Thompson
 */
public class SMSMessageItemReader implements ItemReader<SMSMessage> {

    @Autowired
    private SMSMessageRepositoryWrapper smsMessageRepositoryWrapper;
    private Iterator<SMSMessage> smsMessageIterator;

    @BeforeStep
    public void before(StepExecution stepExecution) {
        //get list of both pending to be sent and wait report for status report
        smsMessageIterator = smsMessageRepositoryWrapper.findByDeliveryStatusIn(Arrays.asList(SMSMessageStatusType.PENDING, SMSMessageStatusType.WAITING_FOR_REPORT)).iterator();
    }

    @Override
    public SMSMessage read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (smsMessageIterator != null && smsMessageIterator.hasNext()) {
            return smsMessageIterator.next();
        } else {
            return null;
        }
    }

}
