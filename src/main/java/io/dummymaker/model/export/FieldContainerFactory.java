package io.dummymaker.model.export;

import static io.dummymaker.util.StringUtils.isEmpty;

import io.dummymaker.annotation.complex.GenSequence;
import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.util.CastUtils;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
public class FieldContainerFactory {

    public FieldContainer build(Field field) {
        final FieldContainer.Type type = isSequential(field)
                ? FieldContainer.Type.SEQUENTIAL
                : getType(field.getType());
        final String exportName = Arrays.stream(field.getDeclaredAnnotations())
                .filter(a -> GenExportName.class.equals(a.annotationType()))
                .map(a -> ((GenExportName) a).value())
                .findFirst()
                .orElse("");

        return build(field, type, exportName);
    }

    private FieldContainer build(Field field, FieldContainer.Type type, String fieldExportName) {
        final String finalName = isEmpty(fieldExportName)
                ? ""
                : fieldExportName;

        if (type.equals(FieldContainer.Type.DATE) && field != null) {
            final GenTime annotation = field.getAnnotation(GenTime.class);
            return new DateFieldContainer(field, type, finalName, annotation);
        }

        return new FieldContainer(field, type, finalName);
    }

    private boolean isSequential(Field field) {
        return Arrays.stream(field.getDeclaredAnnotations()).anyMatch(a -> a.annotationType().equals(GenSequence.class));
    }

    private static FieldContainer.Type getType(final Class<?> type) {
        if (type == null)
            return FieldContainer.Type.STRING;

        if (type.equals(UUID.class)) {
            return FieldContainer.Type.STRING;
        } else if (type.equals(Boolean.class)) {
            return FieldContainer.Type.BOOLEAN;
        } else if (type.equals(Short.class)
                || type.equals(Integer.class)
                || type.equals(Long.class)
                || type.equals(Float.class)
                || type.equals(Double.class)
                || type.equals(BigInteger.class)
                || type.equals(short.class)
                || type.equals(int.class)
                || type.equals(long.class)
                || type.equals(float.class)
                || type.equals(double.class)) {
            return FieldContainer.Type.NUMBER;
        } else if (type.equals(LocalDate.class)
                || type.equals(LocalTime.class)
                || type.equals(LocalDateTime.class)
                || type.equals(OffsetDateTime.class)
                || type.equals(OffsetTime.class)
                || type.equals(Date.class)
                || type.equals(java.sql.Date.class)
                || type.equals(Timestamp.class)
                || type.equals(Time.class)) {
            return FieldContainer.Type.DATE;
        } else if (Iterable.class.isAssignableFrom(type)) {
            return FieldContainer.Type.COLLECTION;
        } else if (Map.class.isAssignableFrom(type)) {
            return FieldContainer.Type.MAP;
        } else if (type.getSimpleName().contains("[][]")) {
            return FieldContainer.Type.ARRAY_2D;
        } else if (type.getSimpleName().contains("[]")) {
            return FieldContainer.Type.ARRAY;
        }

        final CastUtils.CastType castedType = CastUtils.CastType.of(type);
        if (CastUtils.CastType.UNKNOWN.equals(castedType)) {
            return FieldContainer.Type.COMPLEX;
        }

        return FieldContainer.Type.STRING;
    }
}
