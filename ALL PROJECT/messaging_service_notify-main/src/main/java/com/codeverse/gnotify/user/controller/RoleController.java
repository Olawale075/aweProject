/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.controller;

import com.codeverse.gnotify.general.data.ApiConstants;
import static com.codeverse.gnotify.general.data.ApiConstants.ROLE_BASE_URL;
import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.exception.GeneralPlatformDomainRuleException;
import com.codeverse.gnotify.user.domain.Permission;
import com.codeverse.gnotify.user.domain.Role;
import com.codeverse.gnotify.user.service.RoleReadService;
import com.codeverse.gnotify.user.service.RoleWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle.Thompson
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = ROLE_BASE_URL)
public class RoleController {

    private final RoleReadService roleReadService;
    private final RoleWriteService roleWriteService;

    @Autowired
    public RoleController(final RoleReadService roleReadService, final RoleWriteService roleWriteService) {
        this.roleReadService = roleReadService;
        this.roleWriteService = roleWriteService;
    }

    @GetMapping(value = "/{id}", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveOneRole(@PathVariable(value = "id") Long id) {
        final Role role = this.roleReadService.retrieveRole(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping(value = "/permission", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> permissionTemplate() {
        List<Permission> permissionTemplate = this.roleReadService.getPermissions();
        return new ResponseEntity<>(permissionTemplate, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> retrieveRole(
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
        @SortDefault(sort = "id", direction = Sort.Direction.DESC)
    }) @ParameterObject Pageable pageable,
            @RequestParam(value = "name", required = false) String name) {
        final ApiResponseMessage apiResponseMessage = this.roleReadService.retrieveRole(name, pageable);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @PostMapping(
            value = "",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> createRole(@RequestBody @Valid Role role) {
        final ApiResponseMessage apiResponseMessage = this.roleWriteService.createRole(role);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = ApiConstants.MEDIA_TYPE_JSON,
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> updateRole(@RequestBody Role role, @PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.roleWriteService.updateRole(role, id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Long id) {
        final ApiResponseMessage apiResponseMessage = this.roleWriteService.deleteRole(id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    private boolean is(final String commandParam, final String commandValue) {
        return StringUtils.isNotBlank(commandParam) && commandParam.trim().equalsIgnoreCase(commandValue);
    }

    @Operation(summary = "Enable|Disable a Role")
    @PostMapping(
            value = "/{id}",
            produces = ApiConstants.MEDIA_TYPE_JSON)
    public ResponseEntity<?> actionsOnRoles(@PathVariable(value = "id") Long id,
            @Parameter(description = "use command disable|enable")
            @RequestParam(value = "command") String command) {
        ApiResponseMessage apiResponseMessage = null;
        if (is(command, "disable")) {
            apiResponseMessage = this.roleWriteService.disableRole(id);
        } else if (is(command, "enable")) {
            apiResponseMessage = this.roleWriteService.enableRole(id);
        } else {
            throw new GeneralPlatformDomainRuleException("command", command + " is not supported.",
                    new Object[]{"enable", "disable"});
        }
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);

    }
}
