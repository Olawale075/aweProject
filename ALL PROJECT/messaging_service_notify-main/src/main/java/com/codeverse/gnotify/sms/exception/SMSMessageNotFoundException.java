package com.codeverse.gnotify.sms.exception;

import com.codeverse.gnotify.general.exception.AbstractPlatformDomainRuleException;

/**
 * A {@link RuntimeException} thrown when client resources are not found.
 */
public class SMSMessageNotFoundException extends AbstractPlatformDomainRuleException {

    public SMSMessageNotFoundException(final Long id) {
        super("error.sms.msg.id.invalid", "SMS message with identifier " + id + " does not exist", id);
    }

    public SMSMessageNotFoundException() {
        super("error.sms.msg.id.invalid", "SMS message does not exist");
    }

}
