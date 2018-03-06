package io.dummymaker.factory.impl;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.annotation.collection.GenSet;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.impl.ListGenerator;
import io.dummymaker.generator.impl.collection.impl.SetGenerator;
import io.dummymaker.generator.impl.map.impl.MapGenerator;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.impl.EnumerateAnnotationScanner;
import io.dummymaker.scan.impl.PopulateAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Populates objects via PrimeGenAnnotation generators included
 *
 * @author GoodforGod
 * @see PrimeGenAnnotation
 * @see GenEnumerate
 * @since 30.05.2017
 */
public class GenPopulateFactory implements IPopulateFactory {

    private static final Logger logger = Logger.getLogger(GenPopulateFactory.class.getName());

    private final IAnnotationScanner populateScanner = new PopulateAnnotationScanner();
    private final IAnnotationScanner enumerateScanner = new EnumerateAnnotationScanner();

    /**
     * Populate single entity
     *
     * @param t            entity to populate
     * @param enumerateMap map of enumerated marked fields
     * @param generatorMap map where generator is assigned to each entity field
     * @return populated entity
     */
    private <T> T populateEntity(final T t,
                                 final Map<Field, Long> enumerateMap,
                                 final Map<Field, ? extends IGenerator> generatorMap) {
        final boolean haveEnumerateFields = (enumerateMap != null && !enumerateMap.isEmpty());

        final Map<Field, List<Annotation>> classAnnotatedFields = populateScanner.scan(t.getClass());
        final Map<Field, ? extends IGenerator> generatorsFieldMap = (generatorMap == null)
                ? buildGeneratorsMap(classAnnotatedFields)
                : generatorMap;

        for (final Map.Entry<Field, List<Annotation>> annotatedField : classAnnotatedFields.entrySet()) {
            Object objValue = null;
            try {
                annotatedField.getKey().setAccessible(true);

                final Annotation listAnnotation = annotatedField.getValue().stream()
                        .filter(a -> a.annotationType().equals(GenList.class))
                        .findAny().orElse(null);

                if(listAnnotation != null) {
                    objValue = genIfList(annotatedField.getKey(), listAnnotation);
                } else {
                    // Populate enumerated field if it is so or via generator
                    objValue = (haveEnumerateFields && enumerateMap.containsKey(annotatedField.getKey()))
                            ? buildNextEnumeratedValue(enumerateMap, annotatedField.getKey())
                            : generatorsFieldMap.get(annotatedField.getKey()).generate();
                }

                annotatedField.getKey().set(t, annotatedField.getKey().getType().cast(objValue));
            } catch (ClassCastException e) {
                try {
                    // Try to cast object type to string if possible, cause origin type is not castable
                    if (annotatedField.getKey().getType().isAssignableFrom(String.class)) {
                        annotatedField.getKey().set(t, String.valueOf(objValue));
                    }
                } catch (Exception ex) {
                    logger.warning("FIELD TYPE AND GENERATE TYPE ARE NOT COMPATIBLE AND CAN NOT BE CONVERTED TO STRING.");
                }
            } catch (Exception e) {
                logger.warning(e.getMessage());
            } finally {
                annotatedField.getKey().setAccessible(false);
            }
        }

        return t;
    }

    private Object genIfTime(Field field, Annotation annotation) {
        try {
            return null;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    private Object genIfMap(final Field field, final Annotation annotation) {
        try {
            int fixed = ((GenMap) annotation).fixed();
            int min = ((GenMap) annotation).min();
            int max = ((GenMap) annotation).max();
            if(fixed > 0) {
                min = max = fixed;
            }

            final IGenerator keyGenerator = ((GenMap) annotation).key().newInstance();
            final IGenerator valueGenerator = ((GenMap) annotation).value().newInstance();
            final Type keyFieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            final Type valueFieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];

            return new MapGenerator().generate(keyGenerator, valueGenerator,
                    ((Class<?>) keyFieldType), ((Class<?>) valueFieldType),
                    min, max);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    private Object genIfSet(final Field field, final Annotation annotation) {
        try {
            int fixed = ((GenSet) annotation).fixed();
            int min = ((GenSet) annotation).min();
            int max = ((GenSet) annotation).max();
            if(fixed > 0) {
                min = max = fixed;
            }

            final IGenerator generator = ((GenSet) annotation).generator().newInstance();
            final Type fieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

            return new SetGenerator().generate(generator, ((Class<?>) fieldType), min, max);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    private Object genIfList(final Field field, final Annotation annotation) {
        try {
            int fixed = ((GenList) annotation).fixed();
            int min = ((GenList) annotation).min();
            int max = ((GenList) annotation).max();
            if(fixed > 0) {
                min = max = fixed;
            }

            final IGenerator generator = ((GenList) annotation).generator().newInstance();
            final Type fieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

            return new ListGenerator().generate(generator, ((Class<?>) fieldType), min, max);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    /**
     * Build enumerate field next value
     */
    private Object buildNextEnumeratedValue(final Map<Field, Long> enumerateMap,
                                            final Field enumeratedField) {
        Object objValue = enumerateMap.get(enumeratedField);

        if (enumeratedField.getType().isAssignableFrom(Integer.class))
            objValue = Integer.valueOf(String.valueOf(objValue));
        else if (enumeratedField.getType().isAssignableFrom(Long.class))
            objValue = Long.valueOf(String.valueOf(objValue));

        // Increment numerate number for generated field
        enumerateMap.computeIfPresent(enumeratedField, (k, v) -> v + 1);
        return objValue;
    }

    @Override
    public <T> T populate(final T t) {
        return (t == null)
                ? null
                : populateEntity(t, buildEnumerateMap(t.getClass()), null);
    }

    @Override
    public <T> List<T> populate(final List<T> list) {
        if (list == null || list.isEmpty())
            return Collections.emptyList();

        // Set up map for enumerated fields before population
        final Map<Field, Long> enumerateMap = buildEnumerateMap(list.get(0).getClass());
        final Map<Field, IGenerator> generatorMap = buildGeneratorsMap(populateScanner.scan(list.get(0).getClass()));

        return list.stream()
                .filter(Objects::nonNull)
                .map(t -> populateEntity(t, enumerateMap, generatorMap))
                .collect(Collectors.toList());
    }

    /**
     * Build generators map to improve performance
     * So we initialize generators once for entity and not each populate method call
     */
    private Map<Field, IGenerator> buildGeneratorsMap(final Map<Field,  List<Annotation>> map) {
        final Map<Field, IGenerator> generatorsMap = new HashMap<>();

        map.forEach((key, value) -> {
            try {
                final Annotation genAnnotation = value.stream()
                        .filter(a -> a.annotationType().equals(PrimeGenAnnotation.class))
                        .findAny().orElse(null);

                if(genAnnotation != null) {
                    final IGenerator generator = ((PrimeGenAnnotation) genAnnotation).value().newInstance();
                    generatorsMap.put(key, generator);
                }
            } catch (InstantiationException | IllegalAccessException e1) {
                logger.warning(e1.getMessage());
            }
        });

        return generatorsMap;
    }

    /**
     * Setup map for enumerate fields
     *
     * @param t class to scan for enumerate fields
     */
    private Map<Field, Long> buildEnumerateMap(final Class t) {
        return enumerateScanner.scan(t).entrySet().stream()
                    .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> ((GenEnumerate) e.getValue().get(0)).from())
                );
    }
}
