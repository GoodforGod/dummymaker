package io.dummymaker.export;

import static io.dummymaker.util.StringUtils.isEmpty;

import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.annotation.parameterized.GenTime;
import io.dummymaker.annotation.simple.number.GenSequence;
import io.dummymaker.util.CastUtils;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
public final class FieldContainerFactory {

    public ExportField build(Field field) {
        final ExportField.Type type = isSequential(field)
                ? ExportField.Type.SEQUENTIAL
                : getType(field.getType());

        final String exportName = Arrays.stream(field.getDeclaredAnnotations())
                .filter(a -> GenExportName.class.equals(a.annotationType()))
                .map(a -> ((GenExportName) a).value())
                .findFirst()
                .orElse("");

        return build(field, type, exportName);
    }

    private ExportField build(Field field, ExportField.Type type, String fieldExportName) {
        final String finalName = isEmpty(fieldExportName)
                ? ""
                : fieldExportName;

        if (type.equals(ExportField.Type.DATE) && field != null) {
            final GenTime annotation = field.getAnnotation(GenTime.class);
            return new TimeExportField(field, type, finalName, annotation);
        }

        return new ExportField(field, type, finalName);
    }

    private boolean isSequential(Field field) {
        return Arrays.stream(field.getDeclaredAnnotations()).anyMatch(a -> a.annotationType().equals(GenSequence.class));
    }

    private static ExportField.Type getType(final Class<?> type) {
        if (type == null) {
            return ExportField.Type.STRING;
        }

        if (type.equals(UUID.class)) {
            return ExportField.Type.STRING;
        } else if (type.equals(Boolean.class)) {
            return ExportField.Type.BOOLEAN;
        } else if (type.equals(Short.class)
                || type.equals(short.class)
                || type.equals(Integer.class)
                || type.equals(int.class)
                || type.equals(Long.class)
                || type.equals(long.class)
                || type.equals(Float.class)
                || type.equals(float.class)
                || type.equals(Double.class)
                || type.equals(double.class)
                || type.equals(BigInteger.class)
                || type.equals(BigDecimal.class)) {
            return ExportField.Type.NUMBER;
        } else if (type.equals(LocalDate.class)
                || type.equals(LocalTime.class)
                || type.equals(LocalDateTime.class)
                || type.equals(OffsetTime.class)
                || type.equals(OffsetDateTime.class)
                || type.equals(ZonedDateTime.class)
                || type.equals(Instant.class)
                || type.equals(Year.class)
                || type.equals(YearMonth.class)
                || type.equals(MonthDay.class)
                || type.equals(Date.class)
                || type.equals(java.sql.Date.class)
                || type.equals(Timestamp.class)
                || type.equals(Time.class)) {
            return ExportField.Type.DATE;
        } else if (Iterable.class.isAssignableFrom(type)) {
            return ExportField.Type.COLLECTION;
        } else if (Map.class.isAssignableFrom(type)) {
            return ExportField.Type.MAP;
        } else if (type.getSimpleName().contains("[][]")) {
            return ExportField.Type.ARRAY_2D;
        } else if (type.getSimpleName().contains("[]")) {
            return ExportField.Type.ARRAY;
        }

        final CastUtils.CastType castedType = CastUtils.CastType.of(type);
        if (CastUtils.CastType.UNKNOWN.equals(castedType)) {
            return ExportField.Type.COMPLEX;
        }

        return ExportField.Type.STRING;
    }
}
