package com.codeverse.gnotify.sms.repository;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.exception.SMSMessageExternalIdNotFoundException;
import com.codeverse.gnotify.sms.exception.SMSMessageNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SMSMessageRepositoryWrapper {

    private final SMSMessageRepository repository;

    @Autowired
    public SMSMessageRepositoryWrapper(final SMSMessageRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public SMSMessage findOneWithNotFoundDetection(final Long id) {
        final SMSMessage message = this.repository.findById(id).orElseThrow(() -> new SMSMessageNotFoundException(id));
        return message;
    }

    @Transactional(readOnly = true)
    public SMSMessage findExternalIdWithNotFoundDetection(final String externalId) {
        final SMSMessage message = this.repository.findByExternalId(externalId).orElseThrow(() -> new SMSMessageExternalIdNotFoundException(externalId));
        return message;
    }

    @Transactional(readOnly = true)
    public List<SMSMessage> findByDeliveryStatusIn(final List<SMSMessageStatusType> deliveryStatus) {
        return this.repository.findByDeliveryStatusIn(deliveryStatus);
    }

    public void saveAndFlush(final SMSMessage smsMessage) {
        this.repository.saveAndFlush(smsMessage);
    }

    public void saveAllAndFlush(final List<SMSMessage> smsMessage) {
        this.repository.saveAllAndFlush(smsMessage);
    }

    public void delete(final SMSMessage smsMessage) {
        this.repository.delete(smsMessage);
        this.repository.flush();
    }

    @Transactional(readOnly = true)
    public Page<SMSMessage> deliveryReports(Specification<SMSMessage> spec, Pageable pageable) {
        return this.repository.findAll(spec, pageable);
    }
}
