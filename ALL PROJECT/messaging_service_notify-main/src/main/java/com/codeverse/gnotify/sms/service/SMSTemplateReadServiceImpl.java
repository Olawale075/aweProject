/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.service;

import com.codeverse.gnotify.general.data.EnumOptionData;
import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.domain.util.FileSeparator;
import com.codeverse.gnotify.sms.domain.util.LanguageType;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.codeverse.gnotify.sms.repository.SMSTemplateRepositoryWrapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
public class SMSTemplateReadServiceImpl implements SMSTemplateReadService {

    private final SMSTemplateRepositoryWrapper smsTemplateRepositoryWrapper;

    @Autowired
    public SMSTemplateReadServiceImpl(SMSTemplateRepositoryWrapper smsTemplateRepositoryWrapper) {
        this.smsTemplateRepositoryWrapper = smsTemplateRepositoryWrapper;
    }

    @Override
    public List<EnumOptionData> getSMSTypeTemplate() {
        final List<EnumOptionData> listVariableType = new ArrayList<>();
        for (final SMSType smsType : SMSType.values()) {
            EnumOptionData enumOptionData = new EnumOptionData(smsType.getCode());
            listVariableType.add(enumOptionData);
        }
        return listVariableType;
    }

    @Override
    public List<SMSTemplate> retrieveSMSTemplate() {
        return this.smsTemplateRepositoryWrapper.findAll();
    }

    @Override
    public SMSTemplate retrieveSMSTemplate(Long id) {
        return this.smsTemplateRepositoryWrapper.findOneWithNotFoundDetection(id);
    }

    @Override
    public SMSTemplate findBySmsType(final SMSType smsType) {
        return this.smsTemplateRepositoryWrapper.findBySmsType(smsType);
    }

    @Override
    public List<EnumOptionData> languageTypeTemplate() {
        final List<EnumOptionData> listVariableType = new ArrayList<>();
        for (final LanguageType languageType : LanguageType.values()) {
            EnumOptionData enumOptionData = new EnumOptionData(languageType.getCode());
            enumOptionData.setDescription(languageType.getDescription());
            listVariableType.add(enumOptionData);
        }
        return listVariableType;
    }

    @Override
    public List<EnumOptionData> fileSeparatorTemplate() {
        final List<EnumOptionData> listVariableType = new ArrayList<>();
        for (final FileSeparator fileSeparator : FileSeparator.values()) {
            EnumOptionData enumOptionData = new EnumOptionData(fileSeparator.getCode());
            listVariableType.add(enumOptionData);
        }
        return listVariableType;
    }
}
