package com.codeverse.gnotify.sms.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.sms.domain.SMSTemplate;

public interface SMSTemplateWriteService {

    ApiResponseMessage deleteSMSTemplate(final Long id);

    ApiResponseMessage createSMSTemplate(SMSTemplate smsTemplate);

    ApiResponseMessage updateSMSTemplate(SMSTemplate smsTemplate, Long id);
}
