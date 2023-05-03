/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.codeverse.gnotify.sms.domain;

import com.codeverse.gnotify.general.domain.AbstractPersistableCustom;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Entity
@Table(name = "m_sms_message")
public class SMSMessage extends AbstractPersistableCustom<Long> implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sms_template_id", nullable = false)
    private SMSTemplate smsTemplate;

    @Column(name = "sms_type", nullable = true)
    private SMSType smsType;

    @Column(name = "tenant_id", nullable = true)
    private Long tenantId;

    @Column(name = "external_id", nullable = true)
    private String externalId;

    @Column(name = "bulk_id", nullable = true)
    private String bulkId;

    @Column(name = "internal_id", nullable = true)
    private Long internalId;

    @Column(name = "submitted_on_date", nullable = true)
    //@Temporal(TemporalType.TIMESTAMP)
    //private Date submittedOnDate;
    private LocalDateTime submittedOnDate;

    @Column(name = "delivered_on_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveredOnDate;

    @Column(name = "delivery_status", nullable = false)
    private SMSMessageStatusType deliveryStatus = SMSMessageStatusType.PENDING;

    @Column(name = "delivery_error_message", nullable = true)
    private String deliveryErrorMessage;

    @Column(name = "source_address", nullable = true)
    private String sourceAddress;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "sms_bridge_id", nullable = true)
    private Long bridgeId;

    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    public SMSMessage() {
    }

    private SMSMessage(final String externalId, final Long internalId, final Long tenantId,
            final LocalDateTime submittedOnDate, final Date deliveredOnDate,
            final SMSMessageStatusType deliveryStatus, final String deliveryErrorMessage, final String sourceAddress,
            final String mobileNumber, final String message, final Long bridgeId) {
        this.externalId = externalId;
        this.internalId = internalId;
        this.tenantId = tenantId;
        this.submittedOnDate = submittedOnDate;
        this.deliveredOnDate = deliveredOnDate;
        this.deliveryStatus = deliveryStatus;
        this.deliveryErrorMessage = deliveryErrorMessage;
        this.sourceAddress = sourceAddress;
        this.mobileNumber = mobileNumber;
        this.message = message;
        this.bridgeId = bridgeId;
    }

    public static SMSMessage getPendingMessages(final String externalId, final Long internalId,
            final Long tenantId, final LocalDateTime submittedOnDate,
            final Date deliveredOnDate, final String deliveryErrorMessage, final String sourceAddress,
            final String mobileNumber, final String message, final Long providerId) {

        return new SMSMessage(externalId, internalId, tenantId, submittedOnDate,
                deliveredOnDate, SMSMessageStatusType.PENDING, deliveryErrorMessage, sourceAddress, mobileNumber,
                message, providerId);
    }

    /**
     * @param externalId
     * @param internalId
     * @param submittedOnDate
     * @param tenantId
     * @param deliveredOnDate
     * @param deliveryStatus
     * @param mobileNumber
     * @param deliveryErrorMessage
     * @param sourceAddress
     * @param message
     * @param providerId
     * @return an instance of the SmsOutboundMessage class
     *
     */
    public SMSMessage getInstance(final String externalId, final Long internalId, final Long tenantId,
            final LocalDateTime submittedOnDate, final Date deliveredOnDate,
            final SMSMessageStatusType deliveryStatus, final String deliveryErrorMessage, final String sourceAddress,
            final String mobileNumber, final String message, final Long providerId) {

        return new SMSMessage(externalId, internalId, tenantId, submittedOnDate,
                deliveredOnDate, deliveryStatus, deliveryErrorMessage, sourceAddress, mobileNumber, message,
                providerId);
    }

}
