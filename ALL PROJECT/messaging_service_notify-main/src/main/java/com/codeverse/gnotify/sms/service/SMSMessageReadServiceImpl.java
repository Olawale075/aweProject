/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.codeverse.gnotify.general.data.EnumOptionData;
import com.codeverse.gnotify.sms.domain.SMSMessage;
import com.codeverse.gnotify.sms.domain.util.SMSMessageStatusType;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.codeverse.gnotify.sms.repository.SMSMessageRepositoryWrapper;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
@Slf4j
public class SMSMessageReadServiceImpl implements SMSMessageReadService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Lazy
    Job job;

    private final SMSMessageRepositoryWrapper smsMessageRepositoryWrapper;

    @Autowired
    public SMSMessageReadServiceImpl(SMSMessageRepositoryWrapper smsMessageRepositoryWrapper) {
        this.smsMessageRepositoryWrapper = smsMessageRepositoryWrapper;
    }

    @Override
    public List<EnumOptionData> getSMSStatusTemplate() {
        final List<EnumOptionData> listVariableType = new ArrayList<>();
        for (final SMSMessageStatusType smsMessageStatusType : SMSMessageStatusType.values()) {
            if (smsMessageStatusType.getCode().equals(SMSMessageStatusType.INVALID.getCode())) {
                continue;
            }
            EnumOptionData enumOptionData = new EnumOptionData(smsMessageStatusType.getCode());
            listVariableType.add(enumOptionData);
        }
        return listVariableType;
    }

    @Override
    public ApiResponseMessage refreshSMS() {
        String message = "Sms refresh sent";
        Integer status = HttpStatus.PROCESSING.value();
        JobExecution jobExecution = null;
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("SMSTypeName", SMSType.CHEQUE_STATUS.getCode())
                    .toJobParameters();
            this.jobLauncher.run(this.job, jobParameters);
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobRestartException ex) {
            log.error("refreshSMS Error: {}", ex);
            message = ex.getMessage();
            status = HttpStatus.SERVICE_UNAVAILABLE.value();
        }
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage(status, message, jobExecution, null);
        return apiResponseMessage;
    }

    @Override
//    public ApiResponseMessage deliveryReports(SMSType smsType, SMSMessageStatusType deliveryStatus, int size, int page, String[] sort) {
    public ApiResponseMessage deliveryReports(LocalDateTime startPeriod, LocalDateTime endPeriod, String dateFormat, SMSType smsType, SMSMessageStatusType deliveryStatus, Pageable pageable) {
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        String message = "Delivery Reports";
        Integer status;
        try {
            /*List<Order> orders = new ArrayList<>();
            if (sort[0].contains(",")) {
                //?sort=column1,direction1
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = StringUtils.split(sortOrder, ",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                //?sort=column1,direction1&sort=column2,direction2
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));*/

            Page<SMSMessage> smsMessages = this.smsMessageRepositoryWrapper.deliveryReports(deliveryReportsSpecification(
                    dateFormat, startPeriod, endPeriod,
                    smsType, deliveryStatus), pageable);
            if (smsMessages.isEmpty()) {
                message = "No Delivery Reports";
                status = HttpStatus.NO_CONTENT.value();
            } else {
                apiResponseMessage.setData((Serializable) smsMessages);
                status = HttpStatus.OK.value();
            }
        } catch (Exception ex) {
            log.error("deliveryReports Error: {}", ex);
            message = ex.getMessage();
            status = HttpStatus.SERVICE_UNAVAILABLE.value();
        }
        apiResponseMessage.setMessage(message);
        apiResponseMessage.setStatus(status);
        return apiResponseMessage;
    }

    /*private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }*/
    public Specification<SMSMessage> deliveryReportsSpecification(String dateFormat, LocalDateTime fromDate, LocalDateTime toDate, SMSType smsType, SMSMessageStatusType deliveryStatus) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (fromDate != null && toDate != null) {
                if (fromDate != null && toDate != null) {
                    predicates.add(criteriaBuilder.between(root.get("submittedOnDate"), fromDate, toDate));
                    log.info("fromDateTime1: {}", toDate.toString());
                    log.info("fromDateTime1: {}", toDate.toString());
                } else if (fromDate != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("submittedOnDate"), fromDate));
                    log.info("fromDateTime2: {}", fromDate.toString());
                } else if (toDate != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("submittedOnDate"), toDate));
                    log.info("fromDateTime2: {}", toDate.toString());
                }
            }

            if (smsType != null) {
                predicates.add(criteriaBuilder.equal(root.get("smsType"), smsType));
            }
            if (deliveryStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("deliveryStatus"), deliveryStatus));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public SMSMessage retrieveSMSMessage(Long id) {
        return this.smsMessageRepositoryWrapper.findOneWithNotFoundDetection(id);
    }
}
