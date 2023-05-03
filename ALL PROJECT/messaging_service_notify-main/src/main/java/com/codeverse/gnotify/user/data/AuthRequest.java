/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.user.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author Olakunle.Thompson
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class AuthRequest {

    @NotBlank(message = "Username is required")
    private final String username;
    @NotBlank(message = "Password is required")
    private final String password;
}
