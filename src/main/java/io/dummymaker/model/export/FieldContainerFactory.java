package io.dummymaker.model.export;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.*;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.SequenceGenerator;
import io.dummymaker.generator.simple.time.*;

import java.lang.reflect.Field;

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
        final String finalName = isEmpty(exportName) && field != null
                ? field.getName()
                : exportName;

        if (type.equals(FieldContainer.Type.DATETIME) && field != null) {
            final GenTime annotation = field.getAnnotation(GenTime.class);
            return new DatetimeFieldContainer(type, finalName, annotation);
        }

        return new FieldContainer(type, finalName);
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
