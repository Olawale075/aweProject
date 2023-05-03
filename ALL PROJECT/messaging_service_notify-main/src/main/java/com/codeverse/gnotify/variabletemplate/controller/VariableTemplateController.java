/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.variabletemplate.controller;

import com.codeverse.gnotify.general.data.ApiConstants;
import static com.codeverse.gnotify.general.data.ApiConstants.VARIABLE_TEMPLATE_BASE_URL;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.variabletemplate.domain.VariableTemplate;
import com.codeverse.gnotify.variabletemplate.domain.service.VariableTemplateService;
import java.util.List;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle.Thompson
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = VARIABLE_TEMPLATE_BASE_URL)
public class VariableTemplateController {

    private final VariableTemplateService variableTemplateService;

    @Autowired
    public VariableTemplateController(VariableTemplateService variableTemplateService) {
        this.variableTemplateService = variableTemplateService;
    }

    @GetMapping(value = "", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveVariableTemplate(
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
        @SortDefault(sort = "id", direction = Sort.Direction.DESC)
    }) @ParameterObject Pageable pageable) {
        Page<VariableTemplate> variableTemplates = this.variableTemplateService.retrieveVariableTemplate(pageable);
        return new ResponseEntity<>(variableTemplates, HttpStatus.OK);
    }

    @PostMapping(
            value = "",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> createVariableTemplate(@RequestBody VariableTemplate variableTemplate) {
        final ApiResponseMessage apiResponseMessage = this.variableTemplateService.createVariableTemplate(variableTemplate);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> updateVariableTemplate(@RequestBody VariableTemplate variableTemplate, @PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.variableTemplateService.updateVariableTemplate(variableTemplate, id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> deleteVariableTemplate(@PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.variableTemplateService.deleteVariableTemplate(id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }
}
