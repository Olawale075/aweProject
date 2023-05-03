package com.codeverse.gnotify.sms.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.data.EnumOptionData;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface SMSMessageReadService {

    List<EnumOptionData> getSMSStatusTemplate();

    ApiResponseMessage refreshSMS();

    //ApiResponseMessage deliveryReports(SMSType smsType, SMSMessageStatusType deliveryStatus, int size, int page, String[] sort);
    ApiResponseMessage deliveryReports(LocalDateTime startPeriod, LocalDateTime endPeriod, String dateFormat, SMSType smsType, SMSMessageStatusType deliveryStatus, Pageable pageable);

    public SMSMessage retrieveSMSMessage(Long id);
}
