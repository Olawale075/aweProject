/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.codeverse.gnotify;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GNotify {

    public static void main(String[] args) {
        //mustacheTest();

        SpringApplication.run(GNotify.class, args);
    }

    private static void mustacheTest() {
        HashMap<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("name", "Mustache");
        scopes.put("feature", "test");
        scopes.put("toye", "check");

        Writer writer = new OutputStreamWriter(System.out);
        final StringWriter stringWriter = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader("{{name}}, {{feature}}! dsfdffdaf {{toye}}"), "example");
        mustache.execute(stringWriter, scopes);
        try {
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(GNotify.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("writer: " + stringWriter.toString());
    }

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${application.description}") String appDesciption,
            @Value("${application.version}") String appVersion,
            @Value("${codeverse.terms.of.use}") String codeverseTermsOfUse
    ) {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("CodeVerse GNotify")
                        .version(appVersion)
                        .description(appDesciption)
                        .termsOfService(codeverseTermsOfUse)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

    }
}
