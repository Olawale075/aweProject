package com.codeverse.gnotify.sms.service;

import com.codeverse.gnotify.general.data.EnumOptionData;
import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import java.util.List;

public interface SMSTemplateReadService {

    SMSTemplate findBySmsType(final SMSType smsType);

    List<EnumOptionData> getSMSTypeTemplate();

    List<EnumOptionData> languageTypeTemplate();

    List<SMSTemplate> retrieveSMSTemplate();

    SMSTemplate retrieveSMSTemplate(final Long id);

    public List<EnumOptionData> fileSeparatorTemplate();

}
