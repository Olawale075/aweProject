/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.general.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olakunle.Thompson
 */
@Service
@Slf4j
public class TemplateMergeService {

    public String compile(final String smsTypeName, final String messageTemplate, final Map<String, String> scopes) throws IOException {

//        HashMap<String, Object> scopes = new HashMap<String, Object>();
//        scopes.put("name", "Mustache");
//        scopes.put("feature", "test");
//        scopes.put("toye", "check");
        final StringWriter stringWriter = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
//        Mustache mustache = mf.compile(new StringReader("{{name}}, {{feature}}! dsfdffdaf {{toye}}"), "example");
        Mustache mustache = mf.compile(new StringReader(messageTemplate), smsTypeName);
        mustache.execute(stringWriter, scopes);

        return stringWriter.toString();
    }
}
