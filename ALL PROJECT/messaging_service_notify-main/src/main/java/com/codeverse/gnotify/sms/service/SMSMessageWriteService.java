package com.codeverse.gnotify.sms.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import java.util.List;

public interface SMSMessageWriteService {

    ApiResponseMessage createShortMesssage(final List<SMSMessage> messages);

    ApiResponseMessage updateSMSMessage(SMSMessage message, Long id);

}
