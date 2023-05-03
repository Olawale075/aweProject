/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author olakunlethompson
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ChangePasswordData implements Serializable {

    @NotBlank(message = "New password is required")
    private String newPassword;
    @NotBlank(message = "Confirm new password is required")
    private String confirmNewPassword;
    @NotBlank(message = "Old password is required")
    private String oldPassword;
}
