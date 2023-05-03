/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage.listener;

import com.codeverse.gnotify.batching.config.JobExecutionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

/**
 *
 * @author Olakunle.Thompson
 */
@Component
@Slf4j
public class SMSMessageJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("SMSMessage beforeJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("SMSMessage afterJob: " + jobExecution.getStatus());
    }

}
