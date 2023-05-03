/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.notification.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author olakunlethompson
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NotificationTypeConfiguration implements Serializable {

    private String prefix;
    private String baseUrlHost;
    private String port;
    private String username;
    private String passwordToken;
}
