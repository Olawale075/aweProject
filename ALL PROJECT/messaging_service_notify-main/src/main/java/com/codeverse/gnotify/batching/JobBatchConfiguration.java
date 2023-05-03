/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching;

import com.codeverse.gnotify.batching.smsmessage.SMSMessageItemProcessor;
import com.codeverse.gnotify.batching.smsmessage.SMSMessageItemReader;
import com.codeverse.gnotify.batching.smsmessage.SMSMessageItemWriter;
import com.codeverse.gnotify.batching.smsmessage.file.FileCHQStepListener;
import com.codeverse.gnotify.batching.smsmessage.file.FileFTStepListener;
import com.codeverse.gnotify.batching.smsmessage.file.SMSFieldSetMapper_CHQ_FT_ISSUE;
import com.codeverse.gnotify.batching.smsmessage.file.SMSMessagePreparedStatementSetter;
import com.codeverse.gnotify.batching.smsmessage.listener.SMSMessageItemProcessListener;
import com.codeverse.gnotify.batching.smsmessage.listener.SMSMessageItemReaderListener;
import com.codeverse.gnotify.batching.smsmessage.listener.SMSMessageItemWriterListener;
import com.codeverse.gnotify.batching.smsmessage.listener.SMSMessageJobExecutionListener;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.SMSTemplate;
import com.codeverse.gnotify.sms.domain.util.FileSeparator;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.codeverse.gnotify.sms.service.SMSTemplateReadService;
import com.codeverse.gnotify.variabletemplate.domain.repository.VariableTemplateRepositoryWrapper;
import java.io.IOException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 *
 * @author Olakunle.Thompson
 */
@Configuration
@Slf4j
public class JobBatchConfiguration {

    @Value("${sms.batch.chunk}")
    int chunk;

    @Autowired
    public DataSource dataSource;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SMSTemplateReadService smsTemplateReadService;

    @Autowired
    private VariableTemplateRepositoryWrapper variableTemplateRepositoryWrapper;

    // private Resource[] inputResources_CHQ_ISSUE;
    //private String destinationFolder;
    @Bean
    public SMSMessageItemReader reader() {
        return new SMSMessageItemReader();
    }

    @Bean
    public SMSMessageItemProcessor processor() {
        return new SMSMessageItemProcessor();
    }

    @Bean
    public SMSMessageItemWriter writer() {
        return new SMSMessageItemWriter();
    }

    @Bean
    public SMSMessageJobExecutionListener jobExecutionListener() {
        return new SMSMessageJobExecutionListener();
    }

    @Bean
    public SMSMessageItemReaderListener readerListener() {
        return new SMSMessageItemReaderListener();
    }

    @Bean
    public SMSMessageItemProcessListener creditCardItemProcessListener() {
        return new SMSMessageItemProcessListener();
    }

    @Bean
    public SMSMessageItemWriterListener writerListener() {
        return new SMSMessageItemWriterListener();
    }

    @Bean
    public Job jobSMSPendingAndDeliveryReports(
            Step stepSMSPendingAndDeliveryReports,
            //            Step stepSMSFileReader_CHQ_ISSUE,
            SMSMessageJobExecutionListener jobExecutionListener) {
        Job job = jobBuilderFactory.get(SMSMessage.class.getName())
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener)
                .flow(stepSMSPendingAndDeliveryReports)
                .next(stepSMSFileReader_CHQ_ISSUE())
                .next(stepSMSFileReader_FT_ISSUE())
                //.next(FileMoving_CHQ_ISSUE())
                .end()
                .build();
        return job;
    }

    @Bean
    public Step stepSMSPendingAndDeliveryReports(SMSMessageItemReader reader,
            SMSMessageItemWriter writer,
            SMSMessageItemProcessor processor,
            SMSMessageItemReaderListener readerListener,
            SMSMessageItemProcessListener creditCardItemProcessListener,
            SMSMessageItemWriterListener writerListener) {
        TaskletStep step = stepBuilderFactory.get("stepSMSPendingAndDeliveryReports")
                .<SMSMessage, SMSMessage>chunk(this.chunk)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(readerListener)
                .listener(creditCardItemProcessListener)
                .listener(writerListener)
                .build();
        return step;
    }

    /*@Bean
    public SMSTemplate smsTemplate_CHQ_ISSUE() {
        return this.smsTemplateReadService.findBySmsType(SMSType.CHEQUE_STATUS);
    }*/
    @Bean
    public Step stepSMSFileReader_CHQ_ISSUE() {
        return stepBuilderFactory.get("stepSMSFileReader_CHQ_ISSUE")
                .<SMSMessage, SMSMessage>chunk(chunk)
                .reader(multiResourceItemReader_CHQ_ISSUE())
                .writer(multiResourceFileItemWriter())
                .listener(fileStepListener())
                .build();
    }

    @Bean
    public FileCHQStepListener fileStepListener() {
        return new FileCHQStepListener();
    }

    @Bean
    public MultiResourceItemReader<SMSMessage> multiResourceItemReader_CHQ_ISSUE() {
        final SMSTemplate smsTemplate = this.smsTemplateReadService.findBySmsType(SMSType.CHEQUE_STATUS);

        Resource[] inputResources = null;
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            String sourceFolder = StringUtils.appendIfMissingIgnoreCase(smsTemplate.getSourceFolder(), "/*");//append slash if missing
            log.info("sourceFolder: {}", sourceFolder);
            //inputResources = patternResolver.getResources("file:" + "C:/Users/Olakunle.Thompson/Documents/CodeVerse/gNotify App/Documentation/*.txt");
            inputResources = patternResolver.getResources("file:" + sourceFolder);
        } catch (IOException e) {
            log.error("multiResourceItemReader_CHQ_ISSUE Exception: {}", e);
        }
        MultiResourceItemReader<SMSMessage> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(inputResources);
        resourceItemReader.setDelegate(smsFileItemReader_CHQ_ISSUE(smsTemplate));
        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader<SMSMessage> smsFileItemReader_CHQ_ISSUE(final SMSTemplate smsTemplate) {

        log.info("PathMatchingResourcePatternResolver");
        FlatFileItemReader<SMSMessage> reader = new FlatFileItemReader<>();
        if (BooleanUtils.isTrue(smsTemplate.getSkipFileHeader())) {
            reader.setLinesToSkip(1);
        }
        //reader.setResource(new ClassPathResource("/input/input1.txt"));

        DefaultLineMapper<SMSMessage> customerLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"nuban", "phone", "amount", "balance", "time", "date", "debitCredit", "event"});
        final FileSeparator fileSeparator = smsTemplate.getSeparator() == null ? FileSeparator.COMMA : smsTemplate.getSeparator();
        final String separator = StringUtils.defaultIfBlank(fileSeparator.getValue(), FileSeparator.COMMA.getValue());
        tokenizer.setDelimiter(separator);

        customerLineMapper.setLineTokenizer(tokenizer);
        customerLineMapper.setFieldSetMapper(new SMSFieldSetMapper_CHQ_FT_ISSUE(this.smsTemplateReadService, this.variableTemplateRepositoryWrapper));
        customerLineMapper.afterPropertiesSet();
        reader.setLineMapper(customerLineMapper);
        reader.close();
        return reader;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public JdbcBatchItemWriter<SMSMessage> multiResourceFileItemWriter() {
        log.info("multiResourceFileItemWriter");
        JdbcBatchItemWriter<SMSMessage> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("INSERT INTO m_sms_message ( sms_template_id, delivery_status, mobile_number, message, submitted_on_date, sms_type ) VALUES (?,?,?,?,now(),?)");
//        itemWriter.setSql("INSERT INTO m_sms_message VALUES ( sms_bridge_id, delivered_on_date, delivery_error_message, delivery_status, external_id, internal_id, message, mobile_number, response, source_address, submitted_on_date, tenant_id, sms_template_id  ) "
//                + "     VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
//        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
//        itemWriter.afterPropertiesSet();
        ItemPreparedStatementSetter<SMSMessage> valueSetter
                = new SMSMessagePreparedStatementSetter();
        itemWriter.setItemPreparedStatementSetter(valueSetter);
        return itemWriter;
    }

    /*@Bean
    public Step FileMoving_CHQ_ISSUE() {
        FileMoving_CHQ_ISSUE task = new FileMoving_CHQ_ISSUE();
        task.setResources(inputResources_CHQ_ISSUE, this.destinationFolder);
        return stepBuilderFactory.get("FileMoving_CHQ_ISSUE")
                .tasklet(task)
                .build();
    }*/
    //Start Fund Transfer
//    @Bean
//    public SMSTemplate smsTemplate_FT_ISSUE() {
//        return this.smsTemplateReadService.findBySmsType(SMSType.FUND_TRANSFER);
//    }
    @Bean
    public Step stepSMSFileReader_FT_ISSUE() {
        return stepBuilderFactory.get("stepSMSFileReader_FT_ISSUE")
                .<SMSMessage, SMSMessage>chunk(chunk)
                .reader(multiResourceItemReader_FT_ISSUE())
                .writer(multiResourceFileItemWriter())
                .listener(fileFTStepListener())
                .build();
    }

    @Bean
    public ItemReader<? extends SMSMessage> multiResourceItemReader_FT_ISSUE() {
        final SMSTemplate smsTemplate = this.smsTemplateReadService.findBySmsType(SMSType.FUND_TRANSFER);
        Resource[] inputResources = null;
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            String sourceFolder = StringUtils.appendIfMissingIgnoreCase(smsTemplate.getSourceFolder(), "/*");//append slash if missing
            log.info("sourceFolder FT: {}", sourceFolder);
            //inputResources = patternResolver.getResources("file:" + "C:/Users/Olakunle.Thompson/Documents/CodeVerse/gNotify App/Documentation/*.txt");
            inputResources = patternResolver.getResources("file:" + sourceFolder);
        } catch (IOException e) {
            log.error("multiResourceItemReader_FT_ISSUE Exception: {}", e);
        }
        MultiResourceItemReader<SMSMessage> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(inputResources);
        resourceItemReader.setDelegate(smsFileItemReader_FT_ISSUE(smsTemplate));
        return resourceItemReader;
    }

    @Bean
    public FileFTStepListener fileFTStepListener() {
        return new FileFTStepListener();
    }

    @Bean
    public FlatFileItemReader<SMSMessage> smsFileItemReader_FT_ISSUE(final SMSTemplate smsTemplate) {

        FlatFileItemReader<SMSMessage> reader = new FlatFileItemReader<>();
        if (BooleanUtils.isTrue(smsTemplate.getSkipFileHeader())) {
            reader.setLinesToSkip(1);
        }
        //reader.setResource(new ClassPathResource("/input/input1.txt"));

        DefaultLineMapper<SMSMessage> customerLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"amount", "debitCredit", "transactionDescription", "nuban", "balance", "name", "account", "time", "date", "phone", "event"});
        final FileSeparator fileSeparator = smsTemplate.getSeparator() == null ? FileSeparator.COMMA : smsTemplate.getSeparator();
        final String separator = StringUtils.defaultIfBlank(fileSeparator.getValue(), FileSeparator.COMMA.getValue());
        tokenizer.setDelimiter(separator);

        customerLineMapper.setLineTokenizer(tokenizer);
        customerLineMapper.setFieldSetMapper(new SMSFieldSetMapper_CHQ_FT_ISSUE(this.smsTemplateReadService, this.variableTemplateRepositoryWrapper));
        customerLineMapper.afterPropertiesSet();
        reader.setLineMapper(customerLineMapper);
        reader.close();
        return reader;
    }

}
