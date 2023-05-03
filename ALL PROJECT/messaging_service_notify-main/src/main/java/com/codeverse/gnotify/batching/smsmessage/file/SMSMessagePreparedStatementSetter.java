/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.batching.smsmessage.file;

import com.codeverse.gnotify.sms.domain.SMSMessage;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

/**
 *
 * @author Olakunle.Thompson
 */
@Slf4j
public class SMSMessagePreparedStatementSetter implements ItemPreparedStatementSetter<SMSMessage> {

    @Override
    public void setValues(SMSMessage message,
            PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, message.getSmsTemplate().getId());
        preparedStatement.setString(2, message.getDeliveryStatus().name());
        preparedStatement.setString(3, message.getMobileNumber());
        preparedStatement.setString(4, message.getMessage());
        preparedStatement.setString(5, message.getSmsType().name());
        //log.info("SMSMessagePreparedStatementSetter: {}", message.toString());
    }

}
