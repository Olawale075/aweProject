package com.codeverse.gnotify.sms.exception;

import com.codeverse.gnotify.general.exception.AbstractPlatformDomainRuleException;

/**
 * A {@link RuntimeException} thrown when client resources are not found.
 */
public class SMSTemplateNotFoundException extends AbstractPlatformDomainRuleException {

    public SMSTemplateNotFoundException(final Long id) {
        super("error.sms.tmp.id.invalid", "SMS Template with identifier " + id + " does not exist", id);
    }

}
