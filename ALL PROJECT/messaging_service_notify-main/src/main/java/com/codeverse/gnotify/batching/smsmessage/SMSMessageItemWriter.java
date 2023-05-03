/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.provider.infobip.service.InfoBipService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Olakunle.Thompson
 */
public class SMSMessageItemWriter implements ItemWriter<SMSMessage> {

    @Autowired
    private InfoBipService infoBipService;

    @Override
    public void write(List<? extends SMSMessage> list) throws Exception {
        list.forEach(smsMessage -> {
            if (StringUtils.equals(smsMessage.getDeliveryStatus().getCode(), SMSMessageStatusType.PENDING.getCode())) {
                //send sms
                this.infoBipService.sendMessage(smsMessage);
            } else {
                //get report on sms sent
                this.infoBipService.updateStatusByMessageId(smsMessage.getExternalId());
            }
        });
    }

}
