/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage.file;

import com.codeverse.gnotify.general.data.TemplateAgnostic;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.general.service.TemplateMergeService;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.codeverse.gnotify.sms.service.SMSTemplateReadService;
import com.codeverse.gnotify.variabletemplate.domain.VariableTemplate;
import com.codeverse.gnotify.variabletemplate.domain.repository.VariableTemplateRepositoryWrapper;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;

/**
 *
 * @author Olakunle.Thompson
 */
@Slf4j
public class SMSFieldSetMapper_CHQ_FT_ISSUE implements FieldSetMapper<SMSMessage> {

    //final SMSMessage smsMessage = new SMSMessage();
    private final SMSTemplateReadService smsTemplateReadService;
    private final VariableTemplateRepositoryWrapper variableTemplateRepositoryWrapper;

    public SMSFieldSetMapper_CHQ_FT_ISSUE(
            //final SMSTemplate smsTemplate,
            final SMSTemplateReadService smsTemplateReadService,
            final VariableTemplateRepositoryWrapper variableTemplateRepositoryWrapper) {
        //smsMessage.setSmsTemplate(smsTemplate);
        this.smsTemplateReadService = smsTemplateReadService;
        this.variableTemplateRepositoryWrapper = variableTemplateRepositoryWrapper;
    }

    @Override
    public SMSMessage mapFieldSet(FieldSet fieldSet) throws BindException {
        final SMSMessage smsMessage = new SMSMessage();
        //log.info("fieldSet count: {}", fieldSet.getFieldCount());
        String message = null;
        SMSTemplate smsTemplate;
        if (fieldSet.getFieldCount() == 8) {
            //"nuban", "phone", "amount", "balance", "time", "date", "debitCredit", "event"
            smsTemplate = this.smsTemplateReadService.findBySmsType(SMSType.CHEQUE_STATUS);
        } else {
            //FUND TRANSFER
            //"amount", "debitCredit", "transactionDescription", "nuban", "balance", "name", "account", "time", "date", "phone", "event"
            smsTemplate = this.smsTemplateReadService.findBySmsType(SMSType.FUND_TRANSFER);
        }
        if (smsTemplate == null) {
            throw new GeneralPlatformDomainRuleException("error.map.message", "No sms template configured");
        }
        //log.info("mapFieldSet smsTemplate: {}", smsTemplate.toString());
        //log.info("mapFieldSet smsTemplate getSmsType: {}", smsTemplate.getSmsType());
        smsMessage.setSmsType(smsTemplate.getSmsType());
        //log.info("mapFieldSet smsMessage getSmsType: {}", smsMessage.getSmsType());
        smsMessage.setSmsTemplate(smsTemplate);
        final TemplateAgnostic templateAgnostic = smsTemplate.getTemplateAgnostic()
                .stream()
                .filter(action -> BooleanUtils.isTrue(action.getIsActive()))
                .findFirst().orElseThrow(() -> new GeneralPlatformDomainRuleException("error.map.message", "No sms template configured"));

        /*DecimalFormat df = new DecimalFormat("#,###.00");
        final BigDecimal balance = fieldSet.readBigDecimal("balance");
        String balanceValue = df.format(balance);
        final BigDecimal amount = fieldSet.readBigDecimal("amount");
        String amountValue = df.format(amount);*/
        final List<VariableTemplate> variableTemplate = this.variableTemplateRepositoryWrapper.findAll();

        HashMap<String, String> scopes = new HashMap<>();
        if (!CollectionUtils.isEmpty(variableTemplate)) {
            for (VariableTemplate variableTemplate1 : variableTemplate) {
                if (StringUtils.isNotBlank(variableTemplate1.getActionName())) {
                    scopes.put(variableTemplate1.getActionName(), fieldSet.readString(variableTemplate1.getActionName()));
                }
            }
        }
        /*scopes.put("nuban", fieldSet.readString("nuban"));
        scopes.put("amount", amountValue);
        scopes.put("balance", balanceValue);
        scopes.put("time", fieldSet.readString("time"));
        scopes.put("date", fieldSet.readString("date"));
        scopes.put("debitCredit", fieldSet.readString("debitCredit"));
         */
        try {
            final TemplateMergeService templateMergeService = new TemplateMergeService();
            message = templateMergeService.compile(smsTemplate.getSmsType().getCode(), templateAgnostic.getTemplate(), scopes);
        } catch (IOException ex) {
            log.error("compile Error: {}", ex);
        }
        smsMessage.setSubmittedOnDate(LocalDateTime.now());
        //smsMessage.setDeliveredOnDate(fieldSet.readDate("birthdate", "yyyy-MM-dd HH:mm:ss"));

        smsMessage.setDeliveryStatus(SMSMessageStatusType.PENDING);
        smsMessage.setMessage(message);
        String mobileNo = fieldSet.readString("phone");
        final String completePhoneNumber = this.completePhoneNumber(mobileNo, "NG");
        if (StringUtils.isBlank(completePhoneNumber)) {
            smsMessage.setDeliveryStatus(SMSMessageStatusType.FAILED);
            smsMessage.setDeliveryErrorMessage("Invalid phone number");
        } else {
            mobileNo = completePhoneNumber;
        }
        smsMessage.setMobileNumber(mobileNo);
        //smsMessage.setSubmittedOnDate(new Date());
        //log.info("fieldSet values: {}", Arrays.toString(fieldSet.getValues()));
        //log.info("set smsMessage: {}", smsMessage.toString());
        return smsMessage;
    }

    public String completePhoneNumber(final String phoneNumberOnFile, final String region) {
        try {
            PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
            //region default = NG
            PhoneNumber phoneNumber = numberUtil.parse(phoneNumberOnFile, region);
            if (numberUtil.isValidNumber(phoneNumber)) {
                return StringUtils.join(phoneNumber.getCountryCode(), phoneNumber.getNationalNumber());
            }
        } catch (NumberParseException e) {
            log.error("completePhoneNumber: {}", e);
        }
        return null;
    }

}
