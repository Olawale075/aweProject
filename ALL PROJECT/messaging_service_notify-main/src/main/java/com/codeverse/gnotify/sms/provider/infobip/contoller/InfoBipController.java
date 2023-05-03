/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.provider.infobip.contoller;

import com.codeverse.gnotify.general.data.ApiConstants;
import static com.codeverse.gnotify.general.data.ApiConstants.INFOBIP_SMS_TEMPLATE_BASE_URL;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.sms.provider.infobip.service.InfoBipService;
import com.infobip.model.SmsDeliveryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle.Thompson
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = INFOBIP_SMS_TEMPLATE_BASE_URL)
public class InfoBipController {

    private final InfoBipService infoBipService;

    @Autowired
    public InfoBipController(InfoBipService infoBipService) {
        this.infoBipService = infoBipService;
    }

    @PostMapping(
            value = "/report/{smsId}",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> getDeliveryReport(@PathVariable("smsId") final Long smsId, @RequestBody final SmsDeliveryResult payload) {
        final ApiResponseMessage apiResponseMessage = this.infoBipService.getDeliveryReport(smsId, payload);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

}
