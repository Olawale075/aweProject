/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.general.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Olakunle.Thompson
 */
public class DateParam {

    private final String dateAsString;

    public DateParam(final String dateStr) throws Exception {
        this.dateAsString = dateStr;
    }

    @Override
    public String toString() {
        return dateAsString;
    }

    public Date getDate(final String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat);
        final LocalDate localDate = LocalDate.parse(this.dateAsString, dateTimeFormatter);
        return Date.from(localDate.atStartOfDay(getDateTimeZoneOfTenant()).toInstant());
    }

    public static ZoneId getDateTimeZoneOfTenant() {
        ZoneId zone = ZoneId.systemDefault();
        return zone;
    }
}
