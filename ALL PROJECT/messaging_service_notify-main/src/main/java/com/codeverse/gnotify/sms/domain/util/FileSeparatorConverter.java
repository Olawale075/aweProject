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
public class FileSeparatorConverter implements AttributeConverter<FileSeparator, String> {

    @Override
    public String convertToDatabaseColumn(FileSeparator fileSeparator) {
        if (fileSeparator == null) {
            return null;
        }
        return fileSeparator.getCode();
    }

    @Override
    public FileSeparator convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(FileSeparator.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
