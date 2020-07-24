package io.dummymaker.model.export;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.annotation.special.GenSequence;
import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.util.CastUtils;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static io.dummymaker.util.StringUtils.isEmpty;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
public class FieldContainerFactory {

    public FieldContainer build(Field field) {
        return build(field, Cases.DEFAULT.value());
    }

    public FieldContainer build(Field field, ICase naming) {
        final FieldContainer.Type type = isSequential(field) ? FieldContainer.Type.SEQUENTIAL : getType(field.getType());
        final String exportName = Arrays.stream(field.getDeclaredAnnotations())
                .filter(a -> GenExportName.class.equals(a.annotationType()))
                .map(a -> ((GenExportName) a).value())
                .findFirst()
                .orElseGet(() -> naming.format(field.getName()));

        return build(field, type, exportName);
    }

    private FieldContainer build(Field field, FieldContainer.Type type, String exportName) {
        final String finalName = isEmpty(exportName) ? field.getName() : exportName;

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

        if (type.equals(Boolean.class))
            return FieldContainer.Type.BOOLEAN;
        else if (type.equals(Short.class)
                || type.equals(Integer.class)
                || type.equals(Long.class)
                || type.equals(Float.class)
                || type.equals(Double.class)
                || type.equals(BigInteger.class)
                || type.equals(short.class)
                || type.equals(int.class)
                || type.equals(long.class)
                || type.equals(float.class)
                || type.equals(double.class))
            return FieldContainer.Type.NUMBER;
        else if (type.equals(LocalDate.class)
                || type.equals(LocalTime.class)
                || type.equals(LocalDateTime.class)
                || type.equals(Date.class)
                || type.equals(java.sql.Date.class)
                || type.equals(Timestamp.class)
                || type.equals(Time.class))
            return FieldContainer.Type.DATE;
        else if (Iterable.class.isAssignableFrom(type))
            return FieldContainer.Type.COLLECTION;
        else if (Map.class.isAssignableFrom(type))
            return FieldContainer.Type.MAP;
        else if (type.getSimpleName().contains("[][]"))
            return FieldContainer.Type.ARRAY_2D;
        else if (type.getSimpleName().contains("[]"))
            return FieldContainer.Type.ARRAY;

        final CastUtils.CastType castedType = CastUtils.CastType.of(type);
        if (CastUtils.CastType.UNKNOWN.equals(castedType))
            return FieldContainer.Type.COMPLEX;

        return FieldContainer.Type.STRING;
    }
}
