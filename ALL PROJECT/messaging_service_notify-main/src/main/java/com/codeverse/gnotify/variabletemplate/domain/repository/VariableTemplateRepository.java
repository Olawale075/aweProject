/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.variabletemplate.domain.repository;

import com.codeverse.gnotify.variabletemplate.domain.VariableTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Olakunle.Thompson
 */
@Repository
public interface VariableTemplateRepository extends JpaRepository<VariableTemplate, Long> {


}
