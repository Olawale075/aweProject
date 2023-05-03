/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.repository.SMSMessageRepositoryWrapper;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
public class SMSMessageWriteServiceImpl implements SMSMessageWriteService {

    private final SMSMessageRepositoryWrapper smsMessageRepositoryWrapper;

    @Autowired
    public SMSMessageWriteServiceImpl(SMSMessageRepositoryWrapper smsMessageRepositoryWrapper) {
        this.smsMessageRepositoryWrapper = smsMessageRepositoryWrapper;
    }

    @Override
    @Transactional
    public ApiResponseMessage createShortMesssage(List<SMSMessage> messages) {
        try {
            this.smsMessageRepositoryWrapper.saveAllAndFlush(messages);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.create.sms.config", e.getMessage(), "SMS save integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Sms Queued", "Sms created", null);
    }

    @Override
    @Transactional
    public ApiResponseMessage updateSMSMessage(SMSMessage message, Long id) {
        final SMSMessage messageToUpdate = this.smsMessageRepositoryWrapper.findOneWithNotFoundDetection(id);

        try {
            if (StringUtils.isNotBlank(message.getResponse())
                    && !StringUtils.equals(messageToUpdate.getResponse(), message.getResponse())) {
                messageToUpdate.setResponse(message.getResponse());
            }
            if (StringUtils.isNotBlank(message.getDeliveryErrorMessage())
                    && !StringUtils.equals(messageToUpdate.getDeliveryErrorMessage(), message.getDeliveryErrorMessage())) {
                messageToUpdate.setDeliveryErrorMessage(message.getDeliveryErrorMessage());
            }
            if (StringUtils.isNotBlank(message.getExternalId())
                    && !StringUtils.equals(messageToUpdate.getExternalId(), message.getExternalId())) {
                messageToUpdate.setExternalId(message.getExternalId());
            }
            if (StringUtils.isNotBlank(message.getBulkId())
                    && !StringUtils.equals(messageToUpdate.getBulkId(), message.getBulkId())) {
                messageToUpdate.setBulkId(message.getBulkId());
            }
            if (StringUtils.isNotBlank(message.getSourceAddress())
                    && !StringUtils.equals(messageToUpdate.getSourceAddress(), message.getSourceAddress())) {
                messageToUpdate.setSourceAddress(message.getSourceAddress());
            }
            if (message.getDeliveryStatus() != null
                    && !StringUtils.equals(messageToUpdate.getDeliveryStatus().getCode(), message.getDeliveryStatus().getCode())) {
                messageToUpdate.setDeliveryStatus(message.getDeliveryStatus());
            }

            messageToUpdate.setDeliveredOnDate(new Date());
            this.smsMessageRepositoryWrapper.saveAndFlush(messageToUpdate);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.update.sms.config", e.getMessage(), "Update integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Sms updated", message, null);
    }

}
