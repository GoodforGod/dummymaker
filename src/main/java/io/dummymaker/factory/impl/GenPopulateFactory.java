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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final IAnnotationScanner numerateAnnotationScanner = new EnumerateAnnotationScanner();

    private <T> T populateEntity(final T t) {
        return populateEntity(t, null, null);
    }

    /**
     * Populate single entity
     *
     * @param t            entity to populate
     * @param enumerateMap map of enumerated marked fields
     * @param generatorMap map where generator is assigned to each entity field
     * @return populated entity
     */
    private <T> T populateEntity(final T t,
                                 Map<Field, Long> enumerateMap,
                                 Map<Field, ? extends IGenerator> generatorMap) {
        final boolean haveEnumerateFields = (enumerateMap != null && !enumerateMap.isEmpty());

        final Map<Field, List<Annotation>> classAnnotatedFields = populateScanner.scan(t.getClass());
        final Map<Field, ? extends IGenerator> generatorsFieldMap = (generatorMap == null)
                ? buildGeneratorsMap(classAnnotatedFields)
                : generatorMap;

        for (Map.Entry<Field, List<Annotation>> annotatedField : classAnnotatedFields.entrySet()) {
            Object objValue = null;
            try {
                annotatedField.getKey().setAccessible(true);

                if (haveEnumerateFields && enumerateMap.containsKey(annotatedField.getKey())) {
                    objValue = enumerateMap.get(annotatedField.getKey());

                    if (annotatedField.getKey().getType().isAssignableFrom(Integer.class))
                        objValue = Integer.valueOf(String.valueOf(objValue));
                    else if (annotatedField.getKey().getType().isAssignableFrom(Long.class))
                        objValue = Long.valueOf(String.valueOf(objValue));

                    // Increment numerate number for generated field
                    enumerateMap.computeIfPresent(annotatedField.getKey(), (k, v) -> v + 1);
                } else {
                    objValue = generatorsFieldMap.get(annotatedField.getKey()).generate();
                }

                annotatedField.getKey().set(t, annotatedField.getKey().getType().cast(objValue));
            } catch (IllegalAccessException e) {
                logger.warning(e.getMessage());
            } catch (ClassCastException e) {
                try {
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

    @Override
    public <T> T populate(final T t) {
        return populateEntity(t);
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
                .collect(Collectors.toList());
    }

    private Map<Field, IGenerator> buildGeneratorsMap(Map<Field, List<Annotation>> map) {
        final Map<Field, IGenerator> generatorsMap = new HashMap<>();

        map.forEach((key, value) -> {
            try {
                final IGenerator generator = ((PrimeGenAnnotation) value.iterator().next()).value().newInstance();
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
        final Map<Field, List<Annotation>> numerateAnnotations = numerateAnnotationScanner.scan(t);
        if (numerateAnnotations == null || numerateAnnotations.isEmpty())
            return Collections.emptyMap();

        return numerateAnnotations.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> ((GenEnumerate) e.getValue().iterator().next()).from())
                );
    }
}
