package ru.netology;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DateN {
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public String localDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(7);
        Date currentDateTimePlusThreeDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(localDateTime);
    }

}
