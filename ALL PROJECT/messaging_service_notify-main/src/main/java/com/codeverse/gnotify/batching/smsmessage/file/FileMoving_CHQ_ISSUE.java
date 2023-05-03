/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 *
 * @author Olakunle.Thompson
 */
@Slf4j
public class FileMoving_CHQ_ISSUE implements Tasklet, InitializingBean {

    private Resource[] resources;
    private String destinationFolder;

    public void setResources(Resource[] resources, final String destinationFolder) {
        this.resources = resources;
        this.destinationFolder = destinationFolder;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("destinationFolder: {}", destinationFolder);
        for (Resource r : resources) {
            try {
                File fileSource = r.getFile();
                Path sourcePath = fileSource.toPath();
                File fileDestination = new File(destinationFolder + "/" + fileSource.getName());
                Path destinationPath = fileDestination.toPath();
                Files.move(sourcePath, destinationPath);

                /*boolean deleted = fileSource.delete();
                if (!deleted) {
                    throw new UnexpectedJobExecutionException("Could not delete file " + file.getPath());
                }*/
            } catch (IOException e) {
                log.info("Exception: {}", e);
            }
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet");
    }

}
