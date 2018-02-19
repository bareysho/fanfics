package by.bareysho.fanfics.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateCounter {
    public static String dateNow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return dateTimeFormatter.format(LocalDateTime.now());
    }
}