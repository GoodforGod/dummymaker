package io.dummymaker.export.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.error.GenException;
import io.dummymaker.model.export.ClassContainer;
import io.dummymaker.model.export.ExportContainer;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.writer.IWriter;
import io.dummymaker.writer.impl.BufferedFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Time;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static io.dummymaker.util.StringUtils.isBlank;

/**
 * Basic abstract exporter implementation
 *
 * @author GoodforGod
 * @see IExporter
 * @since 25.02.2018
 */
abstract class BasicExporter implements IExporter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Format format;
    private final GenRules rules;
    /**
     * Points as default to directory from where code is running
     */
    private String path;
    private ICase caseUsed;

    /**
     * @param rules    from gen factory
     * @param caseUsed naming strategy
     * @param format   export format
     * @see ICase
     * @see Format
     */
    BasicExporter(Format format, ICase caseUsed, GenRules rules) {
        setPath(path);
        setCase(caseUsed);
        this.format = format;
        this.rules = rules;
    }

    void setPath(final String path) {
        this.path = isBlank(path) ? null : path;
    }

    void setCase(final ICase caseUsed) {
        if (caseUsed != null)
            this.caseUsed = caseUsed;
    }

    /**
     * Build class container with export entity parameters
     */
    <T> ClassContainer buildClassContainer(final T t) {
        return new ClassContainer(t, caseUsed, format, rules);
    }

    /**
     * Build class container with export entity parameters
     */
    <T> ClassContainer buildClassContainer(final List<T> list) {
        return (CollectionUtils.isNotEmpty(list))
                ? buildClassContainer(list.get(0))
                : null;
    }

    /**
     * Build buffered writer for export
     *
     * @see #export(Object)
     * @see #export(List)
     */
    IWriter buildWriter(final ClassContainer classContainer) {
        try {
            return new BufferedFileWriter(classContainer.getExportClassName(), path, format.getExtension());
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    /**
     * Generates class export field-value map
     *
     * @param t class to export
     * @return export containers
     * @see ExportContainer
     */
    <T> List<ExportContainer> extractExportContainers(T t, ClassContainer classContainer) {

        final List<ExportContainer> exports = new ArrayList<>();

        // Using only SIMPLE values containers
        classContainer.getFormatSupported(format).forEach((k, v) -> {
            try {
                k.setAccessible(true);

                final String exportFieldName = v.getExportName();
                final Object exportFieldValue = k.get(t);

                exports.add(buildContainer(exportFieldName, exportFieldValue, k, v));

                k.setAccessible(false);
            } catch (Exception ex) {
                throw new GenException(ex);
            }
        });

        return exports;
    }

    private ExportContainer buildContainer(final String exportFieldName,
                                           final Object exportFieldValue,
                                           final Field field,
                                           final FieldContainer container) {
        if (exportFieldValue == null)
            return ExportContainer.asValue(exportFieldName, "");

        if (this.format.isTypeSupported(container.getType())) {
            switch (container.getType()) {
                case DATETIME:
                    return ExportContainer.asDatetime(exportFieldName, convertAsDatetime(field, exportFieldValue));
                case ARRAY:
                    return ExportContainer.asArray(exportFieldName, convertAsArray(exportFieldValue));
                case ARRAY_2D:
                    return ExportContainer.asArray2D(exportFieldName, Arrays.deepToString((Object[]) exportFieldValue));
                case COLLECTION:
                    return ExportContainer.asList(exportFieldName, exportFieldValue.toString());
                case MAP:
                    return ExportContainer.asMap(exportFieldName, convertAsMap((Map) exportFieldValue));
                default: // will be exported as value after
            }
        }

        return ExportContainer.asValue(exportFieldName, String.valueOf(exportFieldValue));
    }

    private String convertAsArray(Object exportValue) {
        final Class<?> arrayType = exportValue.getClass().getComponentType();
        if (arrayType.equals(byte.class)) {
            return Arrays.toString((byte[]) exportValue);
        } else if (arrayType.equals(short.class)) {
            return Arrays.toString((short[]) exportValue);
        } else if (arrayType.equals(char.class)) {
            return Arrays.toString((char[]) exportValue);
        } else if (arrayType.equals(int.class)) {
            return Arrays.toString((int[]) exportValue);
        } else if (arrayType.equals(long.class)) {
            return Arrays.toString((long[]) exportValue);
        } else if (arrayType.equals(float.class)) {
            return Arrays.toString((float[]) exportValue);
        } else if (arrayType.equals(double.class)) {
            return Arrays.toString((double[]) exportValue);
        }
        return Arrays.toString((Object[]) exportValue);
    }

    @SuppressWarnings("unchecked")
    private String convertAsMap(Map exportMap) {
        return ((Set<Map.Entry>) exportMap.entrySet()).stream()
                .map(e -> "\"" + e.getKey() + "\":\"" + e.getValue() + "\"")
                .collect(Collectors.joining(",", "{", "}"));
    }

    private String convertAsDatetime(Field field, Object exportFieldValue) {
        final GenTime annotation = field.getAnnotation(GenTime.class);
        if (annotation == null)
            return String.valueOf(exportFieldValue);

        try {
            if (annotation.exportAsUnixTime()) {
                return exportAsUnixTime(exportFieldValue);
            } else {
                return exportWithFormatter(annotation, exportFieldValue);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }

        return String.valueOf(exportFieldValue);
    }

    private String exportAsUnixTime(Object exportFieldValue) {
        if (exportFieldValue instanceof Date) {
            return String.valueOf(((Date) exportFieldValue).getTime());
        } else if (exportFieldValue instanceof ChronoLocalDate) {
            return String.valueOf(((LocalDate) exportFieldValue).toEpochDay());
        } else if (exportFieldValue instanceof LocalTime) {
            return String.valueOf(LocalDateTime.of(LocalDate.of(1970, 1, 1),
                    ((LocalTime) exportFieldValue)).toEpochSecond(getZoneOffset()));
        } else if (exportFieldValue instanceof LocalDateTime) {
            return String.valueOf(((LocalDateTime) exportFieldValue).toEpochSecond(getZoneOffset()));
        } else {
            return String.valueOf(exportFieldValue);
        }
    }

    private String exportWithFormatter(GenTime annotation, Object exportFieldValue) {
        if (exportFieldValue instanceof Time) {
            final DateTimeFormatter formatter = GenTime.EXPORT_FORMAT.equals(annotation.formatter())
                    ? DateTimeFormatter.ISO_TIME
                    : DateTimeFormatter.ofPattern(annotation.formatter());

            return LocalDateTime
                    .ofInstant(Instant.ofEpochMilli(((Time) exportFieldValue).getTime()),
                            TimeZone.getDefault().toZoneId())
                    .format(formatter);
        } else if (exportFieldValue instanceof Date) {
            final DateTimeFormatter formatter = GenTime.EXPORT_FORMAT.equals(annotation.formatter())
                    ? DateTimeFormatter.ISO_DATE_TIME
                    : DateTimeFormatter.ofPattern(annotation.formatter());

            return LocalDateTime
                    .ofInstant(Instant.ofEpochMilli(((Date) exportFieldValue).getTime()),
                            TimeZone.getDefault().toZoneId())
                    .format(formatter);
        } else if (exportFieldValue instanceof LocalDate) {
            final DateTimeFormatter formatter = GenTime.EXPORT_FORMAT.equals(annotation.formatter())
                    ? DateTimeFormatter.ISO_DATE
                    : DateTimeFormatter.ofPattern(annotation.formatter());

            return ((LocalDate) exportFieldValue).format(formatter);
        } else if (exportFieldValue instanceof LocalTime) {
            final DateTimeFormatter formatter = GenTime.EXPORT_FORMAT.equals(annotation.formatter())
                    ? DateTimeFormatter.ISO_TIME
                    : DateTimeFormatter.ofPattern(annotation.formatter());

            return ((LocalTime) exportFieldValue).format(formatter);
        } else if (exportFieldValue instanceof LocalDateTime) {
            final DateTimeFormatter formatter = GenTime.EXPORT_FORMAT.equals(annotation.formatter())
                    ? DateTimeFormatter.ISO_DATE_TIME
                    : DateTimeFormatter.ofPattern(annotation.formatter());

            return ((LocalDateTime) exportFieldValue).format(formatter);
        } else {
            return String.valueOf(exportFieldValue);
        }
    }

    private ZoneOffset getZoneOffset() {
        return ZoneOffset.UTC;
    }

    /**
     * Validate export argument
     *
     * @param t class to validate
     * @return validation result
     */
    <T> boolean isExportEntityInvalid(final T t) {
        return (t == null);
    }

    /**
     * Validate export arguments
     *
     * @param t class to validate
     * @return validation result
     */
    <T> boolean isExportEntityInvalid(final List<T> t) {
        return (CollectionUtils.isEmpty(t) || isExportEntityInvalid(t.get(0)));
    }

    /**
     * Validate export arguments
     *
     * @param t class to validate
     * @return validation result
     */
    <T> boolean isExportEntitySingleList(final List<T> t) {
        return (t.size() == 1);
    }
}
