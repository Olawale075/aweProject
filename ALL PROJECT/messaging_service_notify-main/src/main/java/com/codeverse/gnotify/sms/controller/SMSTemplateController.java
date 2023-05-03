/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.controller;

import com.codeverse.gnotify.general.data.ApiConstants;
import static com.codeverse.gnotify.general.data.ApiConstants.SMS_TEMPLATE_BASE_URL;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.data.EnumOptionData;
import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.service.SMSTemplateReadService;
import com.codeverse.gnotify.sms.service.SMSTemplateWriteService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle.Thompson
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = SMS_TEMPLATE_BASE_URL)
public class SMSTemplateController {

    private final SMSTemplateReadService smsTemplateReadService;
    private final SMSTemplateWriteService smsTemplateWriteService;

    @Autowired
    public SMSTemplateController(SMSTemplateReadService smsTemplateReadService, SMSTemplateWriteService smsTemplateWriteService) {
        this.smsTemplateReadService = smsTemplateReadService;
        this.smsTemplateWriteService = smsTemplateWriteService;
    }

    @GetMapping(value = "/smstype", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> smsTypeTemplate() {
        List<EnumOptionData> template = this.smsTemplateReadService.getSMSTypeTemplate();
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    @GetMapping(value = "/fileseparator", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> fileSeparatorTemplate() {
        List<EnumOptionData> template = this.smsTemplateReadService.fileSeparatorTemplate();
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    @GetMapping(value = "/languagetype", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> languageTypeTemplate() {
        List<EnumOptionData> template = this.smsTemplateReadService.languageTypeTemplate();
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveSMSTemplate() {
        List<SMSTemplate> smsTemplates = this.smsTemplateReadService.retrieveSMSTemplate();
        return new ResponseEntity<>(smsTemplates, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveOneSMSTemplate(@PathVariable(value = "id") Long id) {
        SMSTemplate smsTemplate = this.smsTemplateReadService.retrieveSMSTemplate(id);
        return new ResponseEntity<>(smsTemplate, HttpStatus.OK);
    }

    @PostMapping(
            value = "",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> createSMSTemplate(@Valid @RequestBody SMSTemplate smsTemplate) {
        final ApiResponseMessage apiResponseMessage = this.smsTemplateWriteService.createSMSTemplate(smsTemplate);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> updateSMSTemplate(@RequestBody SMSTemplate smsTemplate, @PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.smsTemplateWriteService.updateSMSTemplate(smsTemplate, id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> deleteSMSTemplate(@PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.smsTemplateWriteService.deleteSMSTemplate(id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }
}
