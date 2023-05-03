/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.controller;

import com.codeverse.gnotify.general.data.ApiConstants;
import static com.codeverse.gnotify.general.data.ApiConstants.SMS_MESSAGE_BASE_URL;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.data.EnumOptionData;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.codeverse.gnotify.sms.service.SMSMessageReadService;
import com.codeverse.gnotify.sms.service.SMSMessageWriteService;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import java.util.List;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle.Thompson
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = SMS_MESSAGE_BASE_URL)
public class SMSMessageController {

    private final SMSMessageReadService smsMessageReadService;
    private final SMSMessageWriteService smsMessageWriteService;

    @Autowired
    public SMSMessageController(SMSMessageReadService smsMessageReadService, SMSMessageWriteService smsMessageWriteService) {
        this.smsMessageReadService = smsMessageReadService;
        this.smsMessageWriteService = smsMessageWriteService;
    }

    @GetMapping(value = "/refresh", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> refreshSMS() {
        final ApiResponseMessage apiResponseMessage = this.smsMessageReadService.refreshSMS();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @GetMapping(value = "/template/status", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> statusTemplate() {
        List<EnumOptionData> template = this.smsMessageReadService.getSMSStatusTemplate();
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    @PostMapping(
            value = "",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> sendShortMesssage(@RequestBody final List<SMSMessage> payload) {
        this.smsMessageWriteService.createShortMesssage(payload);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/delivery-reports", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> deliveryReports(
            @Parameter(description = "date format is yyyy-MM-dd HH:mm:ss")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            final LocalDateTime startPeriod,
            @Parameter(description = "date format is yyyy-MM-dd HH:mm:ss")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            final LocalDateTime endPeriod,
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
        //@SortDefault(sort = "deliveredOnDate", direction = Sort.Direction.DESC),
        @SortDefault(sort = "id", direction = Sort.Direction.DESC)
    }) @ParameterObject Pageable pageable,
            @RequestParam(value = "smsType") SMSType smsType,
            @RequestParam(value = "deliveryStatus") SMSMessageStatusType deliveryStatus
    ) {
//        final ApiResponseMessage apiResponseMessage = this.smsMessageReadService.deliveryReports(smsType, deliveryStatus, size, page, sort);
        final ApiResponseMessage apiResponseMessage = this.smsMessageReadService.deliveryReports(
                startPeriod, endPeriod, null,
                smsType, deliveryStatus, pageable);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveOneSMSMessage(@PathVariable(value = "id") Long id) {
        final SMSMessage smsMessage = this.smsMessageReadService.retrieveSMSMessage(id);
        return new ResponseEntity<>(smsMessage, HttpStatus.OK);
    }
}
