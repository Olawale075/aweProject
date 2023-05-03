package com.codeverse.gnotify.sms.exception;

import com.codeverse.gnotify.general.exception.AbstractPlatformDomainRuleException;

/**
 * A {@link RuntimeException} thrown when client resources are not found.
 */
public class SMSMessageExternalIdNotFoundException extends AbstractPlatformDomainRuleException {

    public SMSMessageExternalIdNotFoundException(final String externalId) {
        super("error.sms.msg.externalid.invalid", "SMS external id with identifier " + externalId + " does not exist", externalId);
    }

}
