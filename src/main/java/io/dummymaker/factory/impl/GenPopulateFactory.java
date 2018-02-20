package io.dummymaker.factory.impl;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.scan.EnumerateAnnotationScanner;
import io.dummymaker.scan.IFieldScanner;
import io.dummymaker.scan.PopulateAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Populates objects via PrimeGenAnnotation generators included
 *
 * @see PrimeGenAnnotation
 * @see GenEnumerate
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public class GenPopulateFactory<T> implements IPopulateFactory<T> {

    private final Logger logger = Logger.getLogger(GenPopulateFactory.class.getName());

    private final IFieldScanner populateScanner = new PopulateAnnotationScanner();
    private final IFieldScanner numerateAnnotationScanner = new EnumerateAnnotationScanner();

    /**
     * Populate single entity
     *
     * @param t entity to populate
     * @param enumerateMap map of enumerated marked fields
     * @return populated entity
     */
    private T populateEntity(final T t, Map<Field, Long> enumerateMap) {
        final Map<Field, List<Annotation>> classAnnotatedFields = populateScanner.scan(t.getClass());

        for(Map.Entry<Field, List<Annotation>> annotatedField : classAnnotatedFields.entrySet()) {
            Object objValue = null;
            try {
                annotatedField.getKey().setAccessible(true);

                if(enumerateMap != null
                        && !enumerateMap.isEmpty()
                        && enumerateMap.containsKey(annotatedField.getKey())) {
                    objValue = enumerateMap.get(annotatedField.getKey());

                    if(annotatedField.getKey().getType().isAssignableFrom(Integer.class))
                        objValue = Integer.valueOf(String.valueOf(objValue));

                    if(annotatedField.getKey().getType().isAssignableFrom(Long.class))
                        objValue = Long.valueOf(String.valueOf(objValue));

                    // Increment numerate number for generated field
                    enumerateMap.computeIfPresent(annotatedField.getKey(), (k, v) -> v+1);
                } else {
                    objValue = ((PrimeGenAnnotation) annotatedField.getValue()
                            .iterator().next()).value().newInstance().generate();
                }

                annotatedField.getKey().set(t, annotatedField.getKey().getType().cast(objValue));
            }
            catch (IllegalAccessException e) {
                logger.warning(e.getMessage());
            }
            catch (ClassCastException e) {
                try {
                    if(annotatedField.getKey().getType().isAssignableFrom(String.class)) {
                        annotatedField.getKey().set(t, String.valueOf(objValue));
                    }
                }
                catch (Exception ex) {
                    logger.warning("FIELD TYPE AND GENERATE TYPE ARE NOT COMPATIBLE AND CAN NOT BE CONVERTED TO STRING.");
                }
            }
            catch (Exception e) {
                logger.warning(e.getMessage());
            }
            finally {
                annotatedField.getKey().setAccessible(false);
            }
        }

        return t;
    }

    @Override
    public T populate(final T t) {
        return populateEntity(t, null);
    }

    @Override
    public List<T> populate(final List<T> list) {
        if(list == null || list.isEmpty())
            return Collections.emptyList();

        // Set up map for enumerated fields before population
        final Map<Field, Long> enumerateMap = buildEnumerateMap(list.get(0).getClass());

        return list.stream()
                .map(t -> populateEntity(t, enumerateMap))
                .collect(Collectors.toList());
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
