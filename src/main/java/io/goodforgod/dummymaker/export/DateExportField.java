package io.goodforgod.dummymaker.export;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import java.lang.reflect.Field;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
final class DateExportField extends ExportField {

    private final String formatter;

    DateExportField(Field field, Type type, String exportName) {
        super(field, type, exportName);
        this.formatter = (field.getAnnotation(GenTime.class) == null)
                ? null
                : field.getAnnotation(GenTime.class).formatter();
    }

    String getFormatted(Object date) {
        if (date instanceof Duration) {
            return String.valueOf(((Duration) date).toMillis());
        }

        final DateTimeFormatter formatter = getDateFormatter(date, this.formatter);
        if (date instanceof Date) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(((Date) date).getTime()), TimeZone.getDefault().toZoneId())
                    .format(formatter);
        } else if (date instanceof LocalDate) {
            return ((LocalDate) date).format(formatter);
        } else if (date instanceof LocalTime) {
            return ((LocalTime) date).format(formatter);
        } else if (date instanceof LocalDateTime) {
            return ((LocalDateTime) date).format(formatter);
        } else if (date instanceof OffsetTime) {
            return ((OffsetTime) date).format(formatter);
        } else if (date instanceof OffsetDateTime) {
            return ((OffsetDateTime) date).format(formatter);
        } else if (date instanceof ZonedDateTime) {
            return ((ZonedDateTime) date).format(formatter);
        } else {
            return String.valueOf(date);
        }
    }

    private DateTimeFormatter getDateFormatter(Object date, String formatter) {
        if (date instanceof Time || date instanceof LocalTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof LocalDate) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_DATE
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof Date || date instanceof LocalDateTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_LOCAL_DATE_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof OffsetTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_OFFSET_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof OffsetDateTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof ZonedDateTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_ZONED_DATE_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof Instant) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_INSTANT
                    : DateTimeFormatter.ofPattern(formatter);
        } else {
            return DateTimeFormatter.ofPattern(formatter);
        }
    }
}
