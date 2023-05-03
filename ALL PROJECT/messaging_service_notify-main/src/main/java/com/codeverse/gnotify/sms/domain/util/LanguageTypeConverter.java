/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.domain.util;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Olakunle.Thompson
 */
@Converter(autoApply = true)
public class LanguageTypeConverter implements AttributeConverter<LanguageType, String> {

    @Override
    public String convertToDatabaseColumn(LanguageType languageType) {
        if (languageType == null) {
            return null;
        }
        return languageType.getCode();
    }

    @Override
    public LanguageType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(LanguageType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
