package dev.wresni.common.utilities;

import dev.wresni.common.blueprints.tuples.Pair;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {
    public static final String SIMPLE_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String SIMPLE_ISO_8601_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String ISO_8601_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String COMPLEX_ISO_8601_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String ABBREVIATED_DATETIME_FORMAT = "dd MMM yyyy HH:mm:ss";
    public static final String ABBREVIATED_DATE_FORMAT = "dd MMM yyyy";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String FLIP_DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_INDEX_FORMAT = "yyyyMMddHHmmssSSS";
    private static final int YEAR_DURATION = 365;
    private static final int MONTH_DURATION = 12;
    private static final int WEEK_DURATION = 7;

    public static Collection<String> datetimeFormats() {
        return new HashSet<>(Arrays.asList(
                SIMPLE_DATETIME_FORMAT,
                DATETIME_FORMAT,
                SIMPLE_ISO_8601_DATETIME_FORMAT,
                ISO_8601_DATETIME_FORMAT,
                COMPLEX_ISO_8601_DATETIME_FORMAT,
                ABBREVIATED_DATETIME_FORMAT
        ));
    }

    public static Collection<String> dateFormats() {
        Collection<String> formats = datetimeFormats();
        formats.add(ABBREVIATED_DATE_FORMAT);
        formats.add(DATE_FORMAT);
        formats.add(FLIP_DATE_FORMAT);
        return formats;
    }

    public static String toFormat(LocalDateTime date, String newFormat) {
        try {
            return date.format(DateTimeFormatter.ofPattern(newFormat));
        } catch (Exception e) {
            return null;
        }
    }

    public static String toFormat(Date date, String newFormat) {
        return toFormat(toLocalDateTime(date), newFormat);
    }

    public static String toSimpleDatetimeFormat(LocalDateTime date) {
        return Optional.ofNullable(date)
                .map(localDate -> localDate.format(DateTimeFormatter.ofPattern(SIMPLE_DATETIME_FORMAT)))
                .orElse(null);
    }

    public static String toAbbreviatedFormat(LocalDateTime date) {
        return Optional.ofNullable(date)
                .map(localDate -> localDate.format(DateTimeFormatter.ofPattern(ABBREVIATED_DATETIME_FORMAT)))
                .orElse(null);
    }

    public static String toAbbreviatedFormat(LocalDate date) {
        return Optional.ofNullable(date)
                .map(localDate -> localDate.format(DateTimeFormatter.ofPattern(ABBREVIATED_DATE_FORMAT)))
                .orElse(null);
    }

    public static String toDateTimeIndexFormat(LocalDateTime date) {
        return Optional.ofNullable(date)
                .map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern(DATETIME_INDEX_FORMAT)))
                .orElse(null);
    }

    public static String toTimeFormat(LocalTime time) {
        return Optional.ofNullable(time)
                .map(localTime -> localTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)))
                .orElse(null);
    }

    public static String toTimeFormat(LocalDateTime datetime) {
        return Optional.ofNullable(datetime)
                .map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)))
                .orElse(null);
    }

    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(Date date, ZoneId zoneId) {
        if (Objects.isNull(date)) return null;
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date, ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(String value, String format) {
        if (StringUtil.anyBlank(value, format)) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(value, formatter);
    }

    public static LocalDate toLocalDate(Date date, ZoneId zoneId) {
        if (Objects.isNull(date)) return null;
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date, ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(String value, String format) {
        if (StringUtil.anyBlank(value, format)) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(value, formatter);
    }

    public static LocalTime toLocalTime(Date date, ZoneId zoneId) {
        if (Objects.isNull(date)) return null;
        return date.toInstant().atZone(zoneId).toLocalTime();
    }

    public static LocalTime toLocalTime(Date date) {
        return toLocalTime(date, ZoneId.systemDefault());
    }

    public static LocalTime toLocalTime(String value, String format) {
        if (StringUtil.anyBlank(value, format)) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(value, formatter);
    }

    public static Date toDate(LocalDateTime date, ZoneId zoneId) {
        if (ObjectUtil.anyNull(date, zoneId)) return null;
        return Date.from(date.atZone(zoneId).toInstant());
    }

    public static Date toDate(LocalDateTime date) {
        return toDate(date, ZoneId.systemDefault());
    }

    public static Date toDate(LocalDate date, ZoneId zoneId) {
        if (ObjectUtil.anyNull(date, zoneId)) return null;
        return Date.from(date.atStartOfDay(zoneId).toInstant());
    }

    public static Date toDate(LocalDate date) {
        return toDate(date, ZoneId.systemDefault());
    }

    public static Date toDate(LocalTime time, ZoneId zoneId) {
        if (ObjectUtil.anyNull(time, zoneId)) return null;
        return Date.from(time.atDate(LocalDate.now()).atZone(zoneId).toInstant());
    }

    public static Date toDate(LocalTime time) {
        return toDate(time, ZoneId.systemDefault());
    }

    public static Timestamp toTimestamp(LocalDateTime date) {
        return Objects.isNull(date) ? null : Timestamp.valueOf(date);
    }

    public static Timestamp toTimestamp(LocalDate date, LocalTime time) {
        return ObjectUtil.anyNull(date, time) ? null : Timestamp.valueOf(date.atTime(time));
    }

    public static Timestamp toTimestamp(LocalDate date) {
        return Objects.isNull(date) ? null : Timestamp.valueOf(date.atStartOfDay());
    }

    public static Timestamp toTimestamp(LocalTime time) {
        return Objects.isNull(time) ? null : Timestamp.valueOf(time.atDate(LocalDate.now()));
    }

    public static boolean validateVersion(LocalDateTime input, LocalDateTime existing) {
        if (Objects.isNull(input)) return false;
        if (Objects.isNull(existing)) return true;
        input = input.truncatedTo(ChronoUnit.MILLIS);
        existing = existing.truncatedTo(ChronoUnit.MILLIS);

        return input.equals(existing);
    }

    public static boolean isWeekend(LocalDateTime date) {
        return Objects.nonNull(date) && (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY));
    }

    public static boolean isWeekend(LocalDate date) {
        return Objects.nonNull(date) && isWeekend(date.atStartOfDay());
    }

    public static boolean isWeekend(Timestamp date) {
        return Objects.nonNull(date) && isWeekend(date.toLocalDateTime());
    }

    public static boolean isWeekend(Date date) {
        return Objects.nonNull(date) && isWeekend(toLocalDateTime(date));
    }

    public static boolean isWeekday(LocalDateTime date) {
        return Objects.nonNull(date) && !isWeekend(date);
    }

    public static boolean isWeekday(LocalDate date) {
        return Objects.nonNull(date) && isWeekday(date.atStartOfDay());
    }

    public static boolean isWeekday(Timestamp date) {
        return Objects.nonNull(date) && isWeekday(date.toLocalDateTime());
    }

    public static boolean isWeekday(Date date) {
        return Objects.nonNull(date) && isWeekday(toLocalDateTime(date));
    }



    public static String getDurationDescription(LocalDateTime date) {
        if (Objects.isNull(date)) return null;
        LocalDateTime now = LocalDateTime.now();
        Duration duration = date.isBefore(now) ?
                Duration.between(date, now) :
                Duration.between(now, date);

        if (duration.toDays() > YEAR_DURATION) return String.format("%s years", duration.toDays()/YEAR_DURATION);
        else if (duration.toDays() > MONTH_DURATION) return String.format("%s months", duration.toDays()/MONTH_DURATION);
        else if (duration.toDays() > WEEK_DURATION) return String.format("%s weeks", duration.toDays()/WEEK_DURATION);
        else if (duration.toDays() > 0) return String.format("%s days", duration.toDays());
        else if (duration.toHours() > 0) return String.format("%s hours", duration.toHours());
        else if (duration.toMinutes() > 0) return String.format("%s minutes", duration.toMinutes());
        else return "less than a minute";

    }

    public static Date todayStartTime() {
        return toDate(LocalDate.now().atStartOfDay());
    }

    public static Date todayEndTime() {
        return toDate(LocalDate.now().atTime(LocalTime.MAX));
    }

    public static boolean isBeforeOrEqual(LocalDate startDate, LocalDate endDate) {
        return ObjectUtil.anyNull(startDate, endDate) || (startDate.isBefore(endDate) || startDate.equals(endDate));
    }

    public static boolean isBeforeOrEqualNow(LocalDate startDate) {
        return isBeforeOrEqual(startDate, LocalDate.now());
    }

    public static boolean isAfterOrEqual(LocalDate startDate, LocalDate endDate) {
        return ObjectUtil.anyNull(startDate, endDate) || (startDate.isAfter(endDate) || startDate.equals(endDate));
    }

    public static boolean isAfterOrEqualNow(LocalDate startDate) {
        return isAfterOrEqual(startDate, LocalDate.now());
    }

    public static boolean isBeforeOrEqual(LocalDateTime startDate, LocalDate endDate) {
        return ObjectUtil.anyNull(startDate, endDate) || isBeforeOrEqual(startDate.toLocalDate(), endDate);
    }

    public static boolean isBeforeOrEqualNow(LocalTime time) {
        return Objects.nonNull(time) && (time.isBefore(LocalTime.now()) || time.equals(LocalTime.now()));
    }

    public static boolean isBetween(LocalDate performanceDate, LocalDate startDate, LocalDate endDate) {
        return isAfterOrEqual(performanceDate, startDate) && isBeforeOrEqual(performanceDate, endDate);
    }

    public static Pair<LocalDateTime, LocalDateTime> convertPeriod(String value, LocalDateTime pivot) {
        if (StringUtil.isBlank(value)) return null;
        if (value.equalsIgnoreCase("all")) return null;
        if (Objects.isNull(pivot)) pivot = LocalDateTime.now();

        char identifier = Character.toLowerCase(value.charAt(value.length() - 1));
        int nominal = StringUtil.toInt(value.substring(0, value.length() - 1));
        return switch (identifier) {
            case 'y' -> Pair.of(pivot.minusYears(nominal), pivot);
            case 'm' -> Pair.of(pivot.minusMonths(nominal), pivot);
            case 'w' -> Pair.of(pivot.minusWeeks(nominal), pivot);
            case 'd' -> Pair.of(pivot.minusDays(nominal), pivot);
            case 'h' -> Pair.of(pivot.minusHours(nominal), pivot);
            default -> null;
        };
    }

    public static Pair<LocalDateTime, LocalDateTime> convertPeriod(String value) {
        return convertPeriod(value, LocalDateTime.now());
    }

    public static Pair<LocalDate, LocalDate> convertPeriodToDate(String value) {
        Pair<LocalDateTime, LocalDateTime> result = convertPeriod(value);
        return Objects.isNull(result) ? null : Pair.of(result.first().toLocalDate(), result.second().toLocalDate());
    }

    public static int currentDatetimeMillisIndex() {
        return StringUtil.toInt(toFormat(LocalDateTime.now(), DATETIME_INDEX_FORMAT));
    }
}
