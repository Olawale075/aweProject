package com.codeverse.gnotify.variabletemplate.exception;

import com.codeverse.gnotify.general.exception.AbstractPlatformDomainRuleException;

/**
 * A {@link RuntimeException} thrown when client resources are not found.
 */
public class VariableTemplateNotFoundException extends AbstractPlatformDomainRuleException {

    public VariableTemplateNotFoundException(final Long id) {
        super("error.variable.tmp.id.invalid", "Variable template with identifier " + id + " does not exist", id);
    }

}
