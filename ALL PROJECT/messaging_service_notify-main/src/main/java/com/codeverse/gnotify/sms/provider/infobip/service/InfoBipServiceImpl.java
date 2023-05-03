/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.provider.infobip.service;

import com.codeverse.gnotify.general.config.HostConfig;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.exception.SMSMessageNotFoundException;
import com.codeverse.gnotify.sms.repository.SMSMessageRepositoryWrapper;
import com.codeverse.gnotify.sms.service.SMSMessageWriteService;
import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.Configuration;
import com.infobip.api.SendSmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDeliveryResult;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsResponse;
import com.infobip.model.SmsResponseDetails;
import com.infobip.model.SmsStatus;
import com.infobip.model.SmsTextualMessage;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
@Slf4j
public class InfoBipServiceImpl implements InfoBipService {

    private final SMSMessageRepositoryWrapper smsMessageRepositoryWrapper;
    private final SMSMessageWriteService smsMessageWriteService;
    //private final String callBackUrl;

//    @PostConstruct
//    private void postConstruct() {
//        log.info("postConstruct Start");
//        //final SMSMessage message = this.smsMessageRepositoryWrapper.findOneWithNotFoundDetection(1L);
//        //this.sendMessage(message);
//        List<SMSMessage> smsMessages = this.smsMessageRepositoryWrapper.findByDeliveryStatus(SMSMessageStatusType.WAITING_FOR_REPORT);
//        smsMessages.forEach(smsMessage -> {
//            log.info("smsMessages: {}", smsMessage.toString());
//        });
//        log.info("postConstruct End");
//    }
    @Autowired
    public InfoBipServiceImpl(final SMSMessageWriteService smsMessageWriteService, final HostConfig hostConfig, final SMSMessageRepositoryWrapper smsMessageRepositoryWrapper) {
        this.smsMessageWriteService = smsMessageWriteService;
        this.smsMessageRepositoryWrapper = smsMessageRepositoryWrapper;
        //callBackUrl = String.format("%s://%s:%d%s%s/report/", hostConfig.getProtocol(), hostConfig.getHostName(), hostConfig.getPort(), hostConfig.getApiPath(), INFOBIP_SMS_TEMPLATE_BASE_URL);
        ////callBackUrl = String.format("%s://%s:%d/%s/report/", hostConfig.getProtocol(), hostConfig.getHostName(), hostConfig.getPort(), INFOBIP_SMS_TEMPLATE_BASE_URL);
        this.initApiClient();
    }

    @Override
    public void sendMessage(SMSMessage message) {
        if (message.getId() == null) {
            throw new SMSMessageNotFoundException();
        }
        final Long smsId = message.getId();
        //String statusCallback = callBackUrl + smsId;
        //log.info("callBackUrl: {}", statusCallback);
        SendSmsApi sendSmsApi = new SendSmsApi();
        SmsTextualMessage smsMessage = new SmsTextualMessage()
                .from("InfoSMS")
                .addDestinationsItem(new SmsDestination().to(message.getMobileNumber()))
                .text(message.getMessage());
        //infoBip gives deliveryReports once and you can either use the endPoints ot use the notifyUrl callback
        //we are currently using the Java SDK endPoints
        //smsMessage.setNotifyUrl(statusCallback);
        //smsMessage.setNotifyContentType(ApiConstants.MEDIA_TYPE);

        SmsAdvancedTextualRequest smsMessageRequest = new SmsAdvancedTextualRequest().messages(
                Collections.singletonList(smsMessage)
        );

        try {
            SmsResponse smsResponse = sendSmsApi.sendSmsMessage(smsMessageRequest);
            String bulkId = smsResponse.getBulkId();
            final SmsResponseDetails sentMessageInfo = smsResponse.getMessages().get(0);
            final SmsStatus smsStatus = sentMessageInfo.getStatus();
            String messageId = sentMessageInfo.getMessageId();
            message.setBulkId(bulkId);
            message.setExternalId(messageId);

            message.setSourceAddress(smsMessage.getFrom());
            message.setDeliveryStatus(InfoBipStatus.smsStatus(smsStatus.getGroupId()));
            message.setResponse(smsResponse.toString());
            message.setDeliveryErrorMessage(smsStatus.getDescription());
        } catch (ApiException apiException) {
            // HANDLE THE EXCEPTION
            message.setResponse(apiException.getResponseBody());
            message.setDeliveryStatus(InfoBipStatus.smsStatus(apiException.getCode()));
            message.setDeliveryErrorMessage(apiException.getMessage());
        }
        this.smsMessageWriteService.updateSMSMessage(message, smsId);
    }

    //Fetching delivery reports
    @Override
    public void updateStatusByMessageId(String externalId) {
        log.info("updateStatusByMessageId");
        final SMSMessage message = this.smsMessageRepositoryWrapper.findExternalIdWithNotFoundDetection(externalId);
        try {
            Integer numberOfReportsLimit = 1;
            SendSmsApi sendSmsApi = new SendSmsApi();
            SmsDeliveryResult deliveryReports = sendSmsApi.getOutboundSmsMessageDeliveryReports(message.getBulkId(), message.getExternalId(), numberOfReportsLimit);
            //if (!CollectionUtils.isEmpty(deliveryReports.getResults())) {
            deliveryReports.getResults().forEach(report -> {
                final SmsStatus smsStatus = report.getStatus();
                message.setResponse(report.toString());
                message.setDeliveryStatus(InfoBipStatus.smsStatus(smsStatus.getGroupId()));
                message.setDeliveryErrorMessage(smsStatus.getDescription());
            });
            /*} else {
                message.setDeliveryStatus(SMSMessageStatusType.FAILED);
                message.setResponse("n/a");
                message.setDeliveryErrorMessage("Delivery Failed");
            }*/
            log.info("deliveryReports: {}", deliveryReports.toString());
        } catch (ApiException apiException) {
            message.setResponse(apiException.getResponseBody());
            message.setDeliveryStatus(InfoBipStatus.smsStatus(apiException.getCode()));
            message.setDeliveryErrorMessage(apiException.getMessage());
        }
        this.smsMessageWriteService.updateSMSMessage(message, message.getId());
    }

    private void initApiClient() {
        ApiClient client = new ApiClient();

        final String API_KEY = "42790680d82b2e8ec4bcc3919c5dce04-545872e8-2a0b-443d-9f56-517764bc2cdb";
        final String BASE_URL = "https://jdmdzj.api.infobip.com";

        client.setApiKeyPrefix("App");
        client.setApiKey(API_KEY);
        client.setBasePath(BASE_URL);

        Configuration.setDefaultApiClient(client);
    }

    @Override
    public ApiResponseMessage getDeliveryReport(Long smsId, SmsDeliveryResult payload) {
        log.info("getDeliveryReport - {}", smsId);
        final SMSMessage message = this.smsMessageRepositoryWrapper.findOneWithNotFoundDetection(smsId);
        //SmsReport report = payload.getResults().get(0);
        payload.getResults().forEach(report -> {
            final SmsStatus smsStatus = report.getStatus();
            message.setDeliveryStatus(InfoBipStatus.smsStatus(smsStatus.getGroupId()));
            message.setResponse(report.toString());
            message.setDeliveryErrorMessage(smsStatus.getDescription());
        });
        ApiResponseMessage apiResponseMessage = this.smsMessageWriteService.updateSMSMessage(message, message.getId());
        return apiResponseMessage;
    }
}
