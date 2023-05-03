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
package com.codeverse.gnotify.sms.domain.util;

/**
 * SMS message delivery status predefined enum constants
 *
 */
public enum SMSMessageStatusType {
    INVALID(0, "INVALID"), // unknown status type
    PENDING(100, "PENDING"), // message received
    WAITING_FOR_REPORT(150, "WAITING_FOR_REPORT"),
    SENT(200, "SENT"), // message sent to the SMS gateway
    DELIVERED(300, "DELIVERED"), // SMS gateway's attempt to deliver message to recipient's phone was successful
    FAILED(400, "FAILED"); // SMS gateway's attempt to deliver message to recipient's phone failed

    private final Integer value;
    private final String code;

    /**
     * get enum constant by value
     *
     * @param statusValue the value of the enum constant
     * @return enum constant
     *
     */
    public static SMSMessageStatusType fromInt(final Integer statusValue) {

        SMSMessageStatusType enumeration = SMSMessageStatusType.INVALID;

        switch (statusValue) {
            case 100:
                enumeration = SMSMessageStatusType.PENDING;
                break;
            case 150:
                enumeration = SMSMessageStatusType.WAITING_FOR_REPORT;
                break;
            case 200:
                enumeration = SMSMessageStatusType.SENT;
                break;
            case 300:
                enumeration = SMSMessageStatusType.DELIVERED;
                break;
            case 400:
                enumeration = SMSMessageStatusType.FAILED;
                break;
        }

        return enumeration;
    }

    /**
     * SmsMessageStatusType constructor
     *
     */
    private SMSMessageStatusType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }

    /**
     * @return enum constant value
     *
     */
    public Integer getValue() {
        return this.value;
    }

    /**
     * @return enum constant
     *
     */
    public String getCode() {
        return this.code;
    }
}
