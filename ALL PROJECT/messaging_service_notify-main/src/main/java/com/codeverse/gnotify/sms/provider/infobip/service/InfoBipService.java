/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.provider.infobip.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.infobip.model.SmsDeliveryResult;

/**
 *
 * @author Olakunle.Thompson
 */
public interface InfoBipService {

    void sendMessage(final SMSMessage message);

    void updateStatusByMessageId(final String externalId);

    ApiResponseMessage getDeliveryReport(Long smsId, SmsDeliveryResult payload);
}
