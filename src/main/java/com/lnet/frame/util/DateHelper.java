package com.lnet.frame.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateHelper {

    private static final String[] TRY_FORMATS = new String[]{"yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss", "HH:mm:ss"};

    public static Date parse(Object source, String format) {
        if (source == null || "".equals(source)) return null;
        if (source instanceof Date) return (Date) source;
        if (source instanceof Number) return new Date((Long) source);

        try {
            String dateStr = String.valueOf(source);
            return DateUtils.parseDate(dateStr, format);
        } catch (ParseException ignore) {
            return null;
        }
    }

    public static Date parse(Object source) {
        for (String format : TRY_FORMATS) {
            Date result = parse(source, format);
            if (result != null) return result;
        }
        return null;
    }

}
