package io.dummymaker.factory.impl;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.annotation.collection.GenSet;
import io.dummymaker.annotation.time.GenTime;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.collection.IMapGenerator;
import io.dummymaker.generator.impl.collection.impl.ListGenerator;
import io.dummymaker.generator.impl.collection.impl.MapGenerator;
import io.dummymaker.generator.impl.collection.impl.SetGenerator;
import io.dummymaker.generator.impl.time.impl.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Factory with special generate methods to support complex annotations
 *
 * @see GenTime
 * @see GenList
 * @see GenSet
 * @see GenMap
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
class GenerateFactory {

    private static final Logger logger = Logger.getLogger(GenerateFactory.class.getName());

    /**
     * Method to produce fields value for GenTime annotation
     *
     * @see GenTime
     *
     * @param field field to populate
     * @param annotation fields annotations
     */
    Object generateTimeObject(final Field field,
                              final Annotation annotation) {
        try {
            final long from = ((GenTime) annotation).from();
            final long to = ((GenTime) annotation).to();

            if (field.getType().equals(LocalDateTime.class) || field.getType().equals(Object.class)) {
                return new LocalDateTimeGenerator().generate(from, to);
            } else if (field.getType().equals(LocalDate.class)) {
                return new LocalDateGenerator().generate(from, to);
            } else if (field.getType().equals(LocalTime.class)) {
                return new LocalTimeGenerator().generate(from, to);
            } else if (field.getType().equals(Timestamp.class)) {
                return new TimestampGenerator().generate(from, to);
            } else if (field.getType().equals(Date.class)) {
                return new DateGenerator().generate(from, to);
            } else if (field.getType().equals(String.class)) {
                return String.valueOf(new LocalDateTimeGenerator().generate(from, to));
            }
            return null;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
    }


    /**
     * Method to produce fields value
     *
     * @see GenMap
     *
     * @param field field to populate
     * @param annotation fields annotations
     */
    Object generateMapObject(final Field field,
                             final Annotation annotation,
                             final IMapGenerator<?, ?> mapGenerator) {
        try {
            if (!field.getType().isAssignableFrom(Map.class))
                return null;

            final IMapGenerator<?, ?> usedMapGenerator = (mapGenerator != null)
                    ? mapGenerator
                    : new MapGenerator();

            int fixed = ((GenMap) annotation).fixed();
            int min = ((GenMap) annotation).min();
            int max = ((GenMap) annotation).max();
            if (fixed > 0) {
                min = max = fixed;
            }

            final IGenerator keyGenerator = ((GenMap) annotation).key().newInstance();
            final IGenerator valueGenerator = ((GenMap) annotation).value().newInstance();
            final Type keyFieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            final Type valueFieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];

            return usedMapGenerator.generate(keyGenerator, valueGenerator,
                    ((Class<?>) keyFieldType), ((Class<?>) valueFieldType),
                    min, max);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }


    /**
     * Method to produce fields value
     *
     * @see GenSet
     *
     * @param field field to populate
     * @param annotation fields annotations
     */
    Object generateSetObject(final Field field,
                             final Annotation annotation,
                             final ICollectionGenerator<?> setGenerator) {
        try {
            if (!field.getType().isAssignableFrom(Set.class))
                return null;

            final ICollectionGenerator<?> usedSetGenerator = (setGenerator != null)
                    ? setGenerator
                    : new SetGenerator();

            int fixed = ((GenSet) annotation).fixed();
            int min = ((GenSet) annotation).min();
            int max = ((GenSet) annotation).max();
            if (fixed > 0) {
                min = max = fixed;
            }

            final IGenerator generator = ((GenSet) annotation).generator().newInstance();
            final Type fieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

            return usedSetGenerator.generate(generator, ((Class<?>) fieldType), min, max);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }


    /**
     * Method to produce fields value
     *
     * @see GenList
     *
     * @param field field to populate
     * @param annotation fields annotations
     */
    Object generateListObject(final Field field,
                              final Annotation annotation,
                              final ICollectionGenerator<?> listGenerator) {
        try {
            if (!field.getType().isAssignableFrom(List.class))
                return null;

            final ICollectionGenerator<?> usedListGenerator = (listGenerator != null)
                    ? listGenerator
                    : new ListGenerator();

            int fixed = ((GenList) annotation).fixed();
            int min = ((GenList) annotation).min();
            int max = ((GenList) annotation).max();
            if (fixed > 0) {
                min = max = fixed;
            }

            final IGenerator generator = ((GenList) annotation).generator().newInstance();
            final Type fieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

            return usedListGenerator.generate(generator, ((Class<?>) fieldType), min, max);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }
}
