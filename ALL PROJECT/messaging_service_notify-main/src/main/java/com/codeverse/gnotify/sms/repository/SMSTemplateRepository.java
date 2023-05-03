/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.repository;

import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Olakunle.Thompson
 */
@Repository
public interface SMSTemplateRepository extends JpaRepository<SMSTemplate, Long> {

    Optional<SMSTemplate> findBySmsType(SMSType smsType);

}
