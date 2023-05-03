/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.general.data;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Olakunle.Thompson
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseMessage<T extends Serializable> implements Serializable {

    private int status;
    private String message;
    private T data;
    private String uri;
}
