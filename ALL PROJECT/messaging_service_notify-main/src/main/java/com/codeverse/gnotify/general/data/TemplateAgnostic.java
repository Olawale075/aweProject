/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.general.data;

import com.codeverse.gnotify.sms.domain.util.LanguageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Olakunle.Thompson
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TemplateAgnostic implements Serializable {

    private LanguageType key;
    private String template;
    private Boolean isActive;
}
