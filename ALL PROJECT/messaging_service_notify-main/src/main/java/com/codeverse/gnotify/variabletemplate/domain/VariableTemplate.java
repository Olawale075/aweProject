/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.variabletemplate.domain;

import com.codeverse.gnotify.general.domain.AbstractPersistableCustom;
import com.codeverse.gnotify.sms.domain.*;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Olakunle.Thompson
 */
@Data
@Entity
@Table(name = "m_variable_template")
public class VariableTemplate extends AbstractPersistableCustom<Long> implements Serializable {

    private String actionName;
}
