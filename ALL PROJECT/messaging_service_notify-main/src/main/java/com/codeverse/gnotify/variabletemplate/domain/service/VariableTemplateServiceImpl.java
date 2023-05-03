/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.variabletemplate.domain.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.variabletemplate.domain.VariableTemplate;
import com.codeverse.gnotify.variabletemplate.domain.repository.VariableTemplateRepositoryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
public class VariableTemplateServiceImpl implements VariableTemplateService {

    private final VariableTemplateRepositoryWrapper variableTemplateRepositoryWrapper;

    @Autowired
    public VariableTemplateServiceImpl(VariableTemplateRepositoryWrapper variableTemplateRepositoryWrapper) {
        this.variableTemplateRepositoryWrapper = variableTemplateRepositoryWrapper;
    }

    @Transactional
    @Override
    public ApiResponseMessage updateVariableTemplate(VariableTemplate variableTemplate, Long id) {
        final VariableTemplate variableTemplateToUpdate = this.variableTemplateRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            if (StringUtils.isNotBlank(variableTemplate.getActionName())
                    && !StringUtils.equals(variableTemplateToUpdate.getActionName(), variableTemplate.getActionName())) {
                variableTemplateToUpdate.setActionName(variableTemplate.getActionName());
            }
            this.variableTemplateRepositoryWrapper.saveAndFlush(variableTemplateToUpdate);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.update.variable.config", e.getMessage(), "Update integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Variable Template updated", variableTemplate, null);
    }

    @Transactional
    @Override
    public ApiResponseMessage createVariableTemplate(VariableTemplate variableTemplate) {
        try {
            this.variableTemplateRepositoryWrapper.saveAndFlush(variableTemplate);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.create.variable.config", e.getMessage(), "Create integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.CREATED.value(), "Variable Template created", variableTemplate, null);
    }

    @Transactional
    @Override
    public ApiResponseMessage deleteVariableTemplate(final Long id) {
        final VariableTemplate variableTemplate = this.variableTemplateRepositoryWrapper.findOneWithNotFoundDetection(id);
        try {
            this.variableTemplateRepositoryWrapper.delete(variableTemplate);
        } catch (Exception e) {
            throw new GeneralPlatformDomainRuleException("error.delete.variable.config", e.getMessage(), "Delete integrity issue");
        }
        return new ApiResponseMessage(HttpStatus.OK.value(), "Variable Template deleted", id, null);
    }

    @Override
    public Page<VariableTemplate> retrieveVariableTemplate(Pageable pageable) {
        return this.variableTemplateRepositoryWrapper.findAll(pageable);
    }

}
