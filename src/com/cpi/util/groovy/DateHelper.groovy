package com.cpi.util.groovy

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

class DateHelper {

    // per aggiungere minuti, secondi, giorni, ore, mesi ad un LocalDateTime, usare le funzioni plusHours(n) o plusMinutes(n) o plusDays(n) queste restituiscono
    // una nuova istanza, non modificano quella già creata, perché LocalDateTime è immutable

    public static Supplier<String> getActualDate = LocalDate.now().toString();
    public static Supplier<String> getActualTime = LocalTime.now().toString();
    public static Supplier<String> getActualEpochSeconds = Instant.now().getEpochSecond() + "";
    public static Supplier<String> getActualEpochMillis = Instant.now().toEpochMilli() + "";
    public static Supplier<String> getActualDateTime = LocalDateTime.now().toString();
    public static Supplier<String> getActualMonth = LocalDateTime.now().getMonth().toString();
    public static Supplier<String> getActualDayOfYear = "" + LocalDateTime.now().getDayOfYear();
    public static Supplier<String> getActualDayOfMonth = "" + LocalDateTime.now().get(ChronoField.DAY_OF_MONTH);
    public static Supplier<String> getFirstDayOfNextMonth = "" + LocalDateTime.now().with(TemporalAdjusters.firstDayOfNextMonth()) ;
    public static Supplier<String> getIsLeap = "" + LocalDate.now().isLeapYear(); // verifica se bisestile
    public static Supplier<String> getLocalHour = LocalTime.now().getHour() + "";
    public static Supplier<String> getLocalMinute = LocalTime.now().get(ChronoField.MINUTE_OF_DAY) + ""; //modo alternativo per prendere il minuto
    public static Supplier<String> getLocalSecond = LocalTime.now().getSecond() + "";
    public static BiFunction<LocalDate, LocalDate, String> diffPeriodDays = { localStartDate, localEndDate -> Period.between(localStartDate, localEndDate).getDays() + ""; }
    public static BiFunction<LocalTime, LocalTime, String> diffDurationSeconds = { localStartTime, localEndTime -> Duration.between(localStartTime, localEndTime).getSeconds() + ""; }
    public static BiFunction<LocalTime, LocalTime, String> diffDurationMinutes = { localStartTime, localEndTime -> Duration.between(localStartTime, localEndTime).toMinutes() + ""; }
    // Se volessimo ora e data attuali di una zona determinata potremmo fare così. La zona di sistema è ZoneId.systemDefault()
    public static UnaryOperator<String> getTimeOfZoneWithZoneId = { zoneId -> ZonedDateTime.now(ZoneId.of(zoneId)) + "";}
    public static UnaryOperator<String> getSimpleDateTimeOfZone = { zoneId -> LocalDateTime.now(ZoneId.of(zoneId)) + "";}
    public static Function<Long, Date> convertMillisToDate = { millis -> Date.from( Instant.ofEpochMilli(millis) ); }
    // from Millisecond to SQL Timestamp
    public static Function<Long, Date> convertMillisToSQLDate = { millis -> new java.sql.Timestamp(millis); }
    public static Function<java.sql.Timestamp, Long> getEpochFromSQLDate = { sqlDate -> sqlDate.toInstant().toEpochMilli(); }
    // API per formatter - Parser
    public static String parseLocalDate(String sDate, String sPatternIn, String sPatternOut)
    {
        /*
         * Custom date formatter
         */
        DateTimeFormatter dateTimeFormatterIn = DateTimeFormatter.ofPattern(sPatternIn);
        DateTimeFormatter dateTimeFormatterOut = DateTimeFormatter.ofPattern(sPatternOut);
        LocalDate local = LocalDate.parse(sDate, dateTimeFormatterIn);
        return local.format(dateTimeFormatterOut);
    }

    public static String parseLocalTime(String sDate, String sPatternIn, String sPatternOut)
    {
        /*
         * Custom time formatter
         */
        DateTimeFormatter dateTimeFormatterIn = DateTimeFormatter.ofPattern(sPatternIn);
        DateTimeFormatter dateTimeFormatterOut = DateTimeFormatter.ofPattern(sPatternOut);
        LocalTime local = LocalTime.parse(sDate, dateTimeFormatterIn);
        return local.format(dateTimeFormatterOut);
    }

    public static String parseLocalDateTime(String sDate, String sPatternIn, String sPatternOut)
    {
        /*
         * Custom dateTime formatter
         */
        DateTimeFormatter dateTimeFormatterIn = DateTimeFormatter.ofPattern(sPatternIn);
        DateTimeFormatter dateTimeFormatterOut = DateTimeFormatter.ofPattern(sPatternOut);
        LocalDateTime local = LocalDateTime.parse(sDate, dateTimeFormatterIn);
        return local.format(dateTimeFormatterOut);
    }



    public static String getActualDateAndTime()
    {
        return getActualDateTime.get();
    }
}
