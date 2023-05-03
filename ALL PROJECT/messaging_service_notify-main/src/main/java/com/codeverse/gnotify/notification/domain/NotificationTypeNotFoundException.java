package com.codeverse.gnotify.notification.domain;

import com.codeverse.gnotify.general.exception.AbstractPlatformDomainRuleException;

/**
 * A {@link RuntimeException} thrown when client resources are not found.
 */
public class NotificationTypeNotFoundException extends AbstractPlatformDomainRuleException {

    public NotificationTypeNotFoundException(final Long id) {
        super("error.notification.type.id.invalid", "Notification type with identifier " + id + " does not exist", id);
    }

}
