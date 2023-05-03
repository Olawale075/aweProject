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
package com.codeverse.gnotify.sms.provider.infobip.service;

import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;

public class InfoBipStatus {

    public static SMSMessageStatusType smsStatus(final String infoBipStatus) {
        SMSMessageStatusType smsStatus = SMSMessageStatusType.PENDING;
        switch (infoBipStatus) {
            case "PENDING":
            case "ACCEPTED":
                smsStatus = SMSMessageStatusType.WAITING_FOR_REPORT;
                break;
            case "SENT":
                smsStatus = SMSMessageStatusType.SENT;
                break;
            case "DELIVERED":
                smsStatus = SMSMessageStatusType.DELIVERED;
                break;
            case "UNDELIVERABLE":
            case "EXPIRED":
            case "REJECTED":
                smsStatus = SMSMessageStatusType.FAILED;
                break;
        }
        return smsStatus;
    }

    public static SMSMessageStatusType smsStatus(final Integer infoBipStatus) {
        SMSMessageStatusType smsStatus = SMSMessageStatusType.PENDING;
        switch (infoBipStatus) {
            case 0:
                smsStatus = SMSMessageStatusType.INVALID;
                break;
            case 1:
                smsStatus = SMSMessageStatusType.WAITING_FOR_REPORT;
                break;
            case 2:
            case 4:
            case 5:
                smsStatus = SMSMessageStatusType.FAILED;
                break;

            case 3:
                smsStatus = SMSMessageStatusType.DELIVERED;
                break;
        }
        return smsStatus;
    }
}
