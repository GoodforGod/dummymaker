package io.dummymaker.factory.impl;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.impl.EnumerateAnnotationScanner;
import io.dummymaker.scan.impl.PopulateAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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

    private static final IAnnotationScanner populateScanner = new PopulateAnnotationScanner();
    private static final IAnnotationScanner enumerateAnnotationScanner = new EnumerateAnnotationScanner();

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
        if(t == null)
            return null;

        final boolean haveEnumerateFields = (enumerateMap != null && !enumerateMap.isEmpty());

        final Map<Field, List<Annotation>> classAnnotatedFields = populateScanner.scan(t.getClass());
        final Map<Field, ? extends IGenerator> generatorsFieldMap = (generatorMap == null)
                ? buildGeneratorsMap(classAnnotatedFields)
                : generatorMap;

        for (Map.Entry<Field, List<Annotation>> annotatedField : classAnnotatedFields.entrySet()) {
            Object objValue = null;
            try {
                annotatedField.getKey().setAccessible(true);

                // Populate enumerated field if it is so or via generator
                objValue = (haveEnumerateFields && enumerateMap.containsKey(annotatedField.getKey()))
                        ? buildNextEnumeratedValue(enumerateMap, annotatedField.getKey())
                        : generatorsFieldMap.get(annotatedField.getKey()).generate();

                annotatedField.getKey().set(t, annotatedField.getKey().getType().cast(objValue));
            } catch (IllegalAccessException e) {
                logger.warning(e.getMessage());
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
        return populateEntity(t, null, null);
    }

    @Override
    public <T> List<T> populate(final List<T> list) {
        if (list == null || list.isEmpty())
            return Collections.emptyList();

        // Set up map for enumerated fields before population
        final Map<Field, Long> enumerateMap = buildEnumerateMap(list.get(0).getClass());
        final Map<Field, IGenerator> generatorMap = buildGeneratorsMap(populateScanner.scan(list.get(0).getClass()));

        return list.stream()
                .map(t -> populateEntity(t, enumerateMap, generatorMap))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Build generators map to improve performance
     * So we initialize generators once for entity and not each populate method call
     */
    private Map<Field, IGenerator> buildGeneratorsMap(Map<Field, List<Annotation>> map) {
        final Map<Field, IGenerator> generatorsMap = new HashMap<>();

        map.forEach((key, value) -> {
            try {
                final IGenerator generator = ((PrimeGenAnnotation) value.get(0)).value().newInstance();
                generatorsMap.put(key, generator);
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
        return enumerateAnnotationScanner.scan(t).entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> ((GenEnumerate) e.getValue().get(0)).from())
                );
    }
}
