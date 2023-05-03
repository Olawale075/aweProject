/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.general.service;

import com.codeverse.gnotify.sms.service.SMSMessageReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Olakunle.Thompson
 */
@Component
@Slf4j
public class CronService {

    @Autowired
    private SMSMessageReadService smsMessageReadService;

    /*
    Fields in a cron the expression has the following order:
    ? ? ? ? ? ?
    seconds, minutes, hours, day-of-month, month, day-of-week
     */
    @Async
    @Scheduled(cron = "*/10 * * * * *")
    //@Scheduled(cron = "${cron.expression.eod}", zone = "${lnddo.timezone}")
    public void handleDebitTransfer() {
        //run every 12:15 AM
        log.info("handleDebitTransfer");
    }

    @Async
    @Scheduled(cron = "*/30 * * * * *")
    //@Scheduled(cron = "${cron.expression.telr}", zone = "${odeverse.timezone}")
    public void handleCreditTransfer() {
        //run every 01:15 AM
        log.info("handleCreditTransfer");
    }

    @Async
    @Scheduled(cron = "*/30 * * * * *")
    //@Scheduled(cron = "${cron.expression.telr}", zone = "${odeverse.timezone}")
    public void handleChequeStatus() {
        //run every 01:15 AM
        //this.telrService.calculateEIDs();
        log.info("handleChequeStatus");
    }

    @Async
    @Scheduled(cron = "*/30 * * * * *")
    //@Scheduled(cron = "${cron.expression.telr}", zone = "${odeverse.timezone}")
    public void handleAtmStatus() {
        //run every 01:15 AM
        log.info("handleAtmStatus");
    }

    @Async
    @Scheduled(cron = "*/30 * * * * *")
    //@Scheduled(cron = "${cron.expression.telr}", zone = "${odeverse.timezone}")
    public void handleBalaceNotification() {
        //run every 01:15 AM
        log.info("handleBalaceNotification");
    }

    @Async
    @Scheduled(cron = "*/30 * * * * *")
    //@Scheduled(cron = "${cron.expression.telr}", zone = "${odeverse.timezone}")
    public void handleSmsPendingAndDeliveryReports() {
        //run every 30 secs
        log.info("Start handleSmsDeliveryReports");
        this.smsMessageReadService.refreshSMS();
        log.info("End handleSmsDeliveryReports");
    }

}
