package cpiUtilties

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.TemporalAdjusters
import java.util.function.BiFunction
import java.util.function.Function
import java.util.function.Supplier
import java.util.function.UnaryOperator

class DateHelper {

    // per aggiungere minuti, secondi, giorni, ore, mesi ad un LocalDateTime, usare le funzioni plusHours(n) o plusMinutes(n) o plusDays(n) queste restituiscono
    // una nuova istanza, non modificano quella già creata, perché LocalDateTime è immutable

    public Supplier<String> getActualDate = LocalDate.now().toString()
    public Supplier<String> getActualTime = LocalTime.now().toString()
    public Supplier<String> getActualEpochSeconds = Instant.now().getEpochSecond() + ""
    public Supplier<String> getActualEpochMillis = Instant.now().toEpochMilli() + ""
    public Supplier<String> getActualDateTime = LocalDateTime.now() + ""
    public Supplier<String> getActualMonth = LocalDateTime.now().getMonth() + ""
    public Supplier<String> getActualDayOfYear = "" + LocalDateTime.now().getDayOfYear()
    public Supplier<String> getActualDayOfMonth = "" + LocalDateTime.now().get(ChronoField.DAY_OF_MONTH)
    public Supplier<String> getFirstDayOfNextMonth = "" + LocalDateTime.now().with(TemporalAdjusters.firstDayOfNextMonth()) 
    public  Supplier<String> getIsLeap = "" + LocalDate.now().isLeapYear() // verifica se bisestile
    public  Supplier<String> getLocalHour = LocalTime.now().getHour() + ""
    public  Supplier<String> getLocalMinute = LocalTime.now().get(ChronoField.MINUTE_OF_DAY) + "" //modo alternativo per prendere il minuto
    public  Supplier<String> getLocalSecond = LocalTime.now().getSecond() + ""
    public  BiFunction<LocalDate, LocalDate, String> diffPeriodDays = { LocalDate localStartDate, LocalDate localEndDate -> Period.between(localStartDate, localEndDate).getDays() + "" }
    public  BiFunction<LocalTime, LocalTime, String> diffDurationSeconds = { LocalTime localStartTime, LocalTime localEndTime -> Duration.between(localStartTime, localEndTime).getSeconds() + "" }
    public  BiFunction<LocalTime, LocalTime, String> diffDurationMinutes = { LocalTime localStartTime, LocalTime localEndTime -> Duration.between(localStartTime, localEndTime).toMinutes() + "" }
    // Se volessimo ora e data attuali di una zona determinata potremmo fare così. La zona di sistema è ZoneId.systemDefault()
    public  UnaryOperator<String> getTimeOfZoneWithZoneId = { String zoneId -> ZonedDateTime.now(ZoneId.of(zoneId)) + ""}
    public  UnaryOperator<String> getSimpleDateTimeOfZone = { String zoneId -> LocalDateTime.now(ZoneId.of(zoneId)) + ""}
    public  Function<Long, Date> convertMillisToDate = { millis -> Date.from( Instant.ofEpochMilli(millis) ) }
    // from Millisecond to SQL Timestamp
    public  Function<Long, Date> convertMillisToSQLDate = { millis -> new java.sql.Timestamp(millis) }
    public  Function<java.sql.Timestamp, Long> getEpochFromSQLDate = { sqlDate -> sqlDate.toInstant().toEpochMilli() }
    // API per formatter - Parser
    public  String parseLocalDate(String sDate, String sPatternIn, String sPatternOut)
    {
        /*
         * Custom date formatter
         */
        DateTimeFormatter dateTimeFormatterIn = DateTimeFormatter.ofPattern(sPatternIn)
        DateTimeFormatter dateTimeFormatterOut = DateTimeFormatter.ofPattern(sPatternOut)
        LocalDate local = LocalDate.parse(sDate, dateTimeFormatterIn)
        return local.format(dateTimeFormatterOut)
    }

    public  String parseLocalTime(String sDate, String sPatternIn, String sPatternOut)
    {
        /*
         * Custom time formatter
         */
        DateTimeFormatter dateTimeFormatterIn = DateTimeFormatter.ofPattern(sPatternIn)
        DateTimeFormatter dateTimeFormatterOut = DateTimeFormatter.ofPattern(sPatternOut)
        LocalTime local = LocalTime.parse(sDate, dateTimeFormatterIn)
        return local.format(dateTimeFormatterOut)
    }

    public  String parseLocalDateTime(String sDate, String sPatternIn, String sPatternOut)
    {
        /*
         * Custom dateTime formatter
         */
        DateTimeFormatter dateTimeFormatterIn = DateTimeFormatter.ofPattern(sPatternIn)
        DateTimeFormatter dateTimeFormatterOut = DateTimeFormatter.ofPattern(sPatternOut)
        LocalDateTime local = LocalDateTime.parse(sDate, dateTimeFormatterIn)
        return local.format(dateTimeFormatterOut)
    }
    
    public String getActualDateAndTime()
    {
        return getActualDateTime.get()
    }
    public String sGetActualTimeExtended() // formato Aug 03, 2020 07:04:13 PM
    {
        DateTimeFormatter dateTimeFormatterOut = DateTimeFormatter.ofPattern( "MMM dd, yyyy hh:mm:ss a", Locale.ENGLISH )
        return LocalDateTime.now().format(dateTimeFormatterOut)
    }
}
