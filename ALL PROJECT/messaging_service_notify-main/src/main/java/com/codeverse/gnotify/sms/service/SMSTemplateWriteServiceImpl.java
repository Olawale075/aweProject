/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.repository.SMSTemplateRepositoryWrapper;
import java.util.Date;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
public class SMSTemplateWriteServiceImpl implements SMSTemplateWriteService {

    private final SMSTemplateRepositoryWrapper smsTemplateRepositoryWrapper;

    @Autowired
    public SMSTemplateWriteServiceImpl(SMSTemplateRepositoryWrapper smsTemplateRepositoryWrapper) {
        this.smsTemplateRepositoryWrapper = smsTemplateRepositoryWrapper;
    }

    @Transactional
    @Override
    public ApiResponseMessage updateSMSTemplate(SMSTemplate smsTemplate, Long id) {
        final SMSTemplate smsTemplateToUpdate = this.smsTemplateRepositoryWrapper.findOneWithNotFoundDetection(id);

        try {
            if (StringUtils.isNotBlank(smsTemplate.getShortName())
                    && !StringUtils.equals(smsTemplateToUpdate.getShortName(), smsTemplate.getShortName())) {
                smsTemplateToUpdate.setShortName(smsTemplate.getShortName());
            }
            if (ObjectUtils.isNotEmpty(smsTemplate.getSmsType())
                    && !StringUtils.equals(smsTemplateToUpdate.getSmsType().getCode(), smsTemplate.getSmsType().getCode())) {
                smsTemplateToUpdate.setSmsType(smsTemplate.getSmsType());
            }
            if (!CollectionUtils.isEmpty(smsTemplate.getTemplateAgnostic())) {
                smsTemplateToUpdate.setTemplateAgnostic(smsTemplate.getTemplateAgnostic());
            }
            smsTemplateToUpdate.setLastModifiedDate(new Date());
            this.smsTemplateRepositoryWrapper.saveAndFlush(smsTemplateToUpdate);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.update.engine.config", e.getMessage(), "Update integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Sms Template updated", smsTemplate, null);
    }

    @Transactional
    @Override
    public ApiResponseMessage createSMSTemplate(SMSTemplate smsTemplate) {
        try {
            smsTemplate.setCreatedDate(new Date());
            this.smsTemplateRepositoryWrapper.saveAndFlush(smsTemplate);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.create.engine.config", e.getMessage(), "Create integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.CREATED.value(), "Sms Template created", smsTemplate, null);
    }

    @Transactional
    @Override
    public ApiResponseMessage deleteSMSTemplate(final Long id) {
        final SMSTemplate smsTemplate = this.smsTemplateRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            this.smsTemplateRepositoryWrapper.delete(smsTemplate);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.delete.engine.config", e.getMessage(), "Delete integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Sms Template deleted", id, null);
    }

}
