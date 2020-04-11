package io.dummymaker.model.export;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.*;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.SequenceGenerator;
import io.dummymaker.generator.simple.time.*;
import io.dummymaker.util.CastUtils;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.GenUtils;

import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.dummymaker.util.StringUtils.isEmpty;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 7.3.2020
 */
public class FieldContainerFactory {

    public FieldContainer build(Field field, Class<? extends IGenerator> generator, String exportName) {
        final FieldContainer.Type type = getType(generator);
        return build(field, type, exportName);
    }

    public FieldContainer build(Field field, IGenerator<?> generator, String exportName) {
        final FieldContainer.Type type = getType(generator);
        return build(field, type, exportName);
    }

    private FieldContainer build(Field field, FieldContainer.Type type, String exportName) {
        final String finalName = isEmpty(exportName) && field != null
                ? field.getName()
                : exportName;

        if (type.equals(FieldContainer.Type.DATETIME) && field != null) {
            final GenTime annotation = field.getAnnotation(GenTime.class);
            return new DatetimeFieldContainer(type, finalName, annotation);
        }

        return new FieldContainer(type, finalName);
    }

    private static FieldContainer.Type getType(final IGenerator<?> generator) {
        final List<Class> types = GenUtils.getInterfaceType(generator.getClass());
        if (CollectionUtils.isEmpty(types))
            return FieldContainer.Type.SIMPLE;

        final Class type = types.get(0);
        if (type.equals(LocalDate.class)
                || type.equals(LocalTime.class)
                || type.equals(LocalDateTime.class)
                || type.equals(Date.class)
                || type.equals(java.sql.Date.class)
                || type.equals(Timestamp.class)
                || type.equals(Time.class))
            return FieldContainer.Type.DATETIME;
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
            return FieldContainer.Type.EMBEDDED;

        return FieldContainer.Type.SIMPLE;
    }

    private static FieldContainer.Type getType(final Class<? extends IGenerator> generator) {
        if (generator == null)
            return FieldContainer.Type.SIMPLE;

        if (generator.equals(TimeComplexGenerator.class)
                || generator.equals(LocalDateGenerator.class)
                || generator.equals(LocalTimeGenerator.class)
                || generator.equals(LocalDateTimeGenerator.class)
                || generator.equals(TimeGenerator.class)
                || generator.equals(TimestampGenerator.class)
                || generator.equals(DateGenerator.class)
                || generator.equals(DateSqlGenerator.class))
            return FieldContainer.Type.DATETIME;
        if (generator.equals(SetComplexGenerator.class) || generator.equals(ListComplexGenerator.class)) {
            return FieldContainer.Type.COLLECTION;
        } else if (generator.equals(MapComplexGenerator.class)) {
            return FieldContainer.Type.MAP;
        } else if (generator.equals(EmbeddedGenerator.class)) {
            return FieldContainer.Type.EMBEDDED;
        } else if (generator.equals(SequenceGenerator.class)) {
            return FieldContainer.Type.SEQUENTIAL;
        } else if (generator.equals(ArrayComplexGenerator.class)) {
            return FieldContainer.Type.ARRAY;
        } else if (generator.equals(Array2DComplexGenerator.class)) {
            return FieldContainer.Type.ARRAY_2D;
        }

        return FieldContainer.Type.SIMPLE;
    }
}
