/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage.file;

import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.codeverse.gnotify.sms.service.SMSTemplateReadService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 *
 * @author Olakunle.Thompson
 */
@Slf4j
public class FileCHQStepListener implements StepExecutionListener, ApplicationContextAware {

    private Resource[] resources;
    private ApplicationContext applicationContext;

    @Autowired
    private SMSTemplateReadService smsTemplateReadService;
    private SMSTemplate smsTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("beforeStep");

        MultiResourceItemReader reader = (MultiResourceItemReader) applicationContext.getBean("multiResourceItemReader_CHQ_ISSUE");
        try {
            smsTemplate = this.smsTemplateReadService.findBySmsType(SMSType.CHEQUE_STATUS);
            String sourceFolder = StringUtils.appendIfMissingIgnoreCase(smsTemplate.getSourceFolder(), "/*");

            resources = applicationContext.getResources("file:" + sourceFolder);
            reader.setResources(resources);
        } catch (IOException ex) {
            log.error("Unable to set file resources to bean multiResourceItemReader: {}", ex);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("afterStep");

        if (smsTemplate != null && (stepExecution.getExitStatus().equals(ExitStatus.COMPLETED)
                && stepExecution.getStatus().equals(BatchStatus.COMPLETED)
                && resources.length > 0)) {
            final String destinationFolder = smsTemplate.getDestinationFolder();

            log.info("afterStep destinationFolder: {}", destinationFolder);
            for (Resource r : resources) {
                try {
                    File fileSource = r.getFile();
                    Path sourcePath = fileSource.toPath();
                    File fileDestination = new File(destinationFolder + "/" + fileSource.getName());
                    Path destinationPath = fileDestination.toPath();
                    Files.move(sourcePath, destinationPath);
//                    File oldFile = new File(resource.getFile().getAbsolutePath());
//                    File newFile = new File(resource.getFile().getAbsolutePath() + ".processed");
//                    FileUtils.copyFile(oldFile, newFile);
                    /*boolean deleted = fileSource.delete();
                if (!deleted) {
                    throw new UnexpectedJobExecutionException("Could not delete file " + file.getPath());
                }*/
                } catch (IOException e) {
                    log.info("Exception: {}", e);
                }
            }
        }
        return stepExecution.getExitStatus();
    }
}
