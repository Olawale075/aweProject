package com.codeverse.gnotify.variabletemplate.domain.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.variabletemplate.domain.VariableTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VariableTemplateService {

    Page<VariableTemplate> retrieveVariableTemplate(Pageable pageable);

    ApiResponseMessage deleteVariableTemplate(final Long id);

    ApiResponseMessage createVariableTemplate(VariableTemplate variableTemplate);

    ApiResponseMessage updateVariableTemplate(VariableTemplate variableTemplate, Long id);
}
