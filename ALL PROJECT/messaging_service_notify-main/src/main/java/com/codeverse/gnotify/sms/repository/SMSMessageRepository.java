/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.repository;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Olakunle.Thompson
 */
@Repository
public interface SMSMessageRepository extends JpaRepository<SMSMessage, Long>, JpaSpecificationExecutor<SMSMessage> {

    Optional<SMSMessage> findByExternalId(String externalId);

    List<SMSMessage> findByDeliveryStatusIn(final List<SMSMessageStatusType> deliveryStatus);

    Page<SMSMessage> findByDeliveryStatusOrSmsType(SMSMessageStatusType deliveryStatus, SMSType smsType, Pageable pageable);

}
