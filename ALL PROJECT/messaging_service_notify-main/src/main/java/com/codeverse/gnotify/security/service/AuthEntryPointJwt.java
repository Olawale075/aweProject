/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.security.service;

import com.codeverse.gnotify.general.data.ApiResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author Olakunle.Thompson
 */
@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // log.error("Unauthorized error: {} \n\n {}", authException.getMessage(), authException);
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized user");
        final ApiResponseMessage apiResponseMessage = new ApiResponseMessage();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponseMessage.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        apiResponseMessage.setMessage(authException.getMessage());
        apiResponseMessage.setUri(request.getServletPath());

        //response.getOutputStream().println(objectMapper.writeValueAsString(apiResponseMessage));
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), apiResponseMessage);
    }

}
