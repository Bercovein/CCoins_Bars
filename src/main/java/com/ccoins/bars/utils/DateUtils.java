package com.ccoins.bars.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtils {

    private DateUtils() {
    }

    public static final String AUTO_DATE = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP";

    public static final String HH_MM = "HH:mm";

    public static final String DDMMYYYY_HHMM = "DD/MM/YYYY HH:mm";

    public static LocalDateTime now(){
        return LocalDateTime.now();
    }

    public static boolean isBetween(LocalTime start, LocalTime end, LocalTime time) {
        if (start.isAfter(end)) {
            return !time.isBefore(start) || !time.isAfter(end);
        } else {
            return !time.isBefore(start) && !time.isAfter(end);
        }
    }
}
