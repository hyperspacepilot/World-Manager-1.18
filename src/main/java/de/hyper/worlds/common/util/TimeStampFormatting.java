package de.hyper.worlds.common.util;

import de.hyper.worlds.common.enums.TimeUnitPattern;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeStampFormatting {

    public static String GERMAN_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static String getGermanDateAndTime(long timestamp) {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("Germany"), Locale.GERMANY);
        Date date = new Date(timestamp);
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(GERMAN_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Germany"));
        return dateFormat.format(date);
    }

    public static String getDateDifference(long from, long to, String colorCode) {
        return formatLong(to - from, colorCode);
    }

    public static String formatLong(long l, String colorCode) {
        long years = l / 31536000000L;
        long secsleftafteryears = l % 31536000000L;
        long months = secsleftafteryears / 2592000000L;
        long secsleftaftermonths = secsleftafteryears % 2592000000L;
        long days = secsleftaftermonths / 86400000L;
        long secsleftafterdays = secsleftaftermonths % 86400000L;
        long hours = secsleftafterdays / 3600000L;
        long secsleftafterhours = secsleftafterdays % 3600000L;
        long minutes = secsleftafterhours / 60000L;
        long seconds = (secsleftafterhours % 60000L) / 1000;
        String year = (years > 0 ? (years + "§7y ") : "");
        String month = (months > 0 ? (months + "§7mo ") : "");
        String day = (days > 0 ? (days + "§7d ") : "");
        String hour = (hours > 0 ? (hours + "§7h ") : "");
        String min = (minutes > 0 ? (minutes + "§7min ") : "");
        String second = (seconds > 0 ? (seconds + "§7s ") : "");
        return "§" + colorCode + year
                + "§" + colorCode + month
                + "§" + colorCode + day
                + "§" + colorCode + hour
                + "§" + colorCode + min
                + "§" + colorCode + second
                + "§7";
    }

    public static long convertLongFormString(String input) {
        long result = 0L;
        for (String inputPart : input.split(" ")) {
            long unitValue = Converter.getPositiveLong(inputPart);
            String unitType = inputPart.replaceAll("[bfgjlpqvxzüöä0-9]", "");
            if (TimeUnitPattern.SECONDS.matches(unitType)) {
                result += (unitValue * 1000);
            } else if (TimeUnitPattern.MINUTES.matches(unitType)) {
                result += (unitValue * (1000 * 60));
            } else if (TimeUnitPattern.HOURS.matches(unitType)) {
                result += (unitValue * (1000 * 60 * 60));
            } else if (TimeUnitPattern.DAYS.matches(unitType)) {
                result += (unitValue * (1000 * 60 * 60 * 24));
            } else if (TimeUnitPattern.WEEKS.matches(unitType)) {
                result += (unitValue * (1000 * 60 * 60 * 24 * 7));
            }
        }
        return result;
    }
}