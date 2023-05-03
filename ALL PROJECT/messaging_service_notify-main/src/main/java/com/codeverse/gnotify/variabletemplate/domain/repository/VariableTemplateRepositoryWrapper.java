package com.codeverse.gnotify.variabletemplate.domain.repository;

import com.codeverse.gnotify.variabletemplate.domain.VariableTemplate;
import com.codeverse.gnotify.variabletemplate.exception.VariableTemplateNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class VariableTemplateRepositoryWrapper {

    private final VariableTemplateRepository repository;

    @Autowired
    public VariableTemplateRepositoryWrapper(final VariableTemplateRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public VariableTemplate findOneWithNotFoundDetection(final Long id) {
        final VariableTemplate variableTemplate = this.repository.findById(id).orElseThrow(() -> new VariableTemplateNotFoundException(id));
        return variableTemplate;
    }

    @Transactional(readOnly = true)
    public Page<VariableTemplate> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<VariableTemplate> findAll() {
        return this.repository.findAll();
    }

    public void saveAndFlush(final VariableTemplate engineConfig) {
        this.repository.saveAndFlush(engineConfig);
    }

    public void delete(final VariableTemplate engineConfig) {
        this.repository.delete(engineConfig);
        this.repository.flush();
    }
}
