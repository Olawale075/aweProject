package com.codeverse.gnotify.sms.repository;

import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.codeverse.gnotify.sms.exception.SMSTemplateNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SMSTemplateRepositoryWrapper {

    private final SMSTemplateRepository repository;

    @Autowired
    public SMSTemplateRepositoryWrapper(final SMSTemplateRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public SMSTemplate findOneWithNotFoundDetection(final Long id) {
        final SMSTemplate client = this.repository.findById(id).orElseThrow(() -> new SMSTemplateNotFoundException(id));
        return client;
    }

    @Transactional(readOnly = true)
    public SMSTemplate findBySmsType(final SMSType smsType) {
        return this.repository.findBySmsType(smsType).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<SMSTemplate> findAll() {
        return this.repository.findAll();
    }

    public void saveAndFlush(final SMSTemplate engineConfig) {
        this.repository.saveAndFlush(engineConfig);
    }

    public void delete(final SMSTemplate engineConfig) {
        this.repository.delete(engineConfig);
        this.repository.flush();
    }
}
