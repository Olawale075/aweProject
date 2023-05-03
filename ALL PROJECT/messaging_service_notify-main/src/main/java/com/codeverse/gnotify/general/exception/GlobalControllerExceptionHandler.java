/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.general.exception;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * @author Olakunle.Thompson
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler //extends ResponseEntityExceptionHandler 
{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.info("handleMethodArgumentNotValid");

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        //return new ResponseEntity<>(body, headers, status);
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        apiResponseMessage.setMessage(ex.getMessage());
        apiResponseMessage.setData((Serializable) errors);
        return new ResponseEntity<>(apiResponseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        Object value = ex.getValue();
        String message = String.format("'%s' should be a valid '%s' and '%s' isn't",
                name, type, value);
        log.info("handleMethodArgumentTypeMismatchException");
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        apiResponseMessage.setUri(request.getDescription(false));
        apiResponseMessage.setMessage(message);
        apiResponseMessage.setData(ex.getMessage());
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseMessage> handleConnversion(RuntimeException ex, WebRequest request) {
        log.info("handleConnversion");
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        apiResponseMessage.setUri(request.getDescription(false));
        apiResponseMessage.setMessage("Bad request sent to server");
        apiResponseMessage.setData(ex.getMessage());
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ClassNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponseMessage> classNotFoundException(ClassNotFoundException ex) {
        log.info("invocationTargetException");
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponseMessage.setMessage("Could not resolve class on server");
        apiResponseMessage.setData(ex.getMessage());
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvocationTargetException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponseMessage> invocationTargetException(InvocationTargetException ex) {
        log.info("invocationTargetException");
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponseMessage.setMessage("Could not resolve action on server");
        apiResponseMessage.setData(ex.getMessage());
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.info("resourceNotFound");
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(HttpStatus.NOT_FOUND.value());
        apiResponseMessage.setUri(request.getDescription(false));
        apiResponseMessage.setMessage("Resource not found");
        apiResponseMessage.setData(ex.getMessage());
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponseMessage> handleAllExceptions(
            Exception ex, WebRequest request) {
        log.info("handleAllExceptions");
        String errorMessage = ex.getMessage();

        if (StringUtils.isBlank(errorMessage)) {
            errorMessage = "Service error , Please contact the admin";
        }

        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponseMessage.setUri(request.getDescription(false));
        apiResponseMessage.setMessage("Server error");
        apiResponseMessage.setData(errorMessage);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
