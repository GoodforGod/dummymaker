package io.dummymaker.factory.impl;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.annotation.collection.GenSet;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.time.GenTime;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.collection.IMapGenerator;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.impl.EnumerateAnnotationScanner;
import io.dummymaker.scan.impl.PopulateAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static io.dummymaker.util.BasicCastUtils.castObject;

/**
 * Populates objects via PrimeGen generators included
 *
 * @see PrimeGen
 * @see GenEnumerate
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public class GenPopulateFactory implements IPopulateFactory {

    private static final Logger logger = Logger.getLogger(GenPopulateFactory.class.getName());

    private final IAnnotationScanner populateScanner = new PopulateAnnotationScanner();
    private final IAnnotationScanner enumerateScanner = new EnumerateAnnotationScanner();

    private final GenerateFactory generateFactory = new GenerateFactory();

    /**
     * Populate single entity
     *
     * @param t             entity to populate
     * @param enumeratesMap map of enumerated marked fields
     * @param generatorsMap map where generator is assigned to each entity field
     * @return populated entity
     */
    private <T> T populateEntity(final T t,
                                 final Map<Field, Long> enumeratesMap,
                                 final Map<Field, ? extends IGenerator> generatorsMap) {
        final Map<Field, List<Annotation>> classAnnotatedFields = populateScanner.scan(t.getClass());

        for (final Map.Entry<Field, List<Annotation>> annotatedField : classAnnotatedFields.entrySet()) {
            final Field field = annotatedField.getKey();
            try {
                field.setAccessible(true);

                final Object objValue = buildObject(field,
                        annotatedField.getValue(),
                        generatorsMap,
                        enumeratesMap);

                field.set(t, objValue);
            } catch (ClassCastException e) {
                logger.warning("FIELD TYPE AND GENERATE TYPE ARE NOT COMPATIBLE.");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            } finally {
                annotatedField.getKey().setAccessible(false);
            }
        }

        return t;
    }

    /**
     * Build field populate value
     *
     * @param field field to populate
     * @param annotations fields annotation
     * @param generatorsMap fields generators
     * @param enumerateMap field enumerate map
     */
    private Object buildObject(final Field field,
                               final List<Annotation> annotations,
                               final Map<Field, ? extends IGenerator> generatorsMap,
                               final Map<Field, Long> enumerateMap) {
        final Annotation listAnnotation = annotations.stream()
                .filter(a -> a.annotationType().equals(GenList.class))
                .findAny().orElse(null);

        final Annotation setAnnotation = annotations.stream()
                .filter(a -> a.annotationType().equals(GenSet.class))
                .findAny().orElse(null);

        final Annotation mapAnnotation = annotations.stream()
                .filter(a -> a.annotationType().equals(GenMap.class))
                .findAny().orElse(null);

        final Annotation timeAnnotation = annotations.stream()
                .filter(a -> a.annotationType().equals(GenTime.class))
                .findAny().orElse(null);

        final IGenerator generator = generatorsMap.get(field);

        if (listAnnotation != null) {
            return generateFactory.generateListObject(field, listAnnotation, ((ICollectionGenerator) generator));
        } else if (setAnnotation != null) {
            return generateFactory.generateSetObject(field, setAnnotation, ((ICollectionGenerator) generator));
        } else if (mapAnnotation != null) {
            return generateFactory.generateMapObject(field, mapAnnotation, ((IMapGenerator) generator));
        } else if (timeAnnotation != null) {
            return generateFactory.generateTimeObject(field, timeAnnotation);
        } else if (enumerateMap.containsKey(field)) {
            return buildNextEnumeratedValue(enumerateMap, field);
        } else {
            final Object generated = generator.generate();
            return castObject(generated, generated.getClass(), field.getType());
        }
    }

    /**
     * Build enumerate field next value
     */
    private Object buildNextEnumeratedValue(final Map<Field, Long> enumerateMap,
                                            final Field enumeratedField) {
        Object objValue = enumerateMap.get(enumeratedField);

        if (enumeratedField.getType().isAssignableFrom(Integer.class)) {
            objValue = Integer.valueOf(String.valueOf(objValue));
        } else if (enumeratedField.getType().isAssignableFrom(Long.class)) {
            objValue = Long.valueOf(String.valueOf(objValue));
        }

        // Increment numerate number for generated field
        enumerateMap.computeIfPresent(enumeratedField, (k, v) -> v + 1);
        return objValue;
    }

    @Override
    public <T> T populate(final T t) {
        if (t == null)
            return null;

        return populateEntity(t,
                buildEnumerateMap(t.getClass()),
                buildGeneratorsMap(populateScanner.scan(t.getClass())));
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
    private Map<Field, IGenerator> buildGeneratorsMap(final Map<Field, List<Annotation>> map) {
        final Map<Field, IGenerator> generatorsMap = new HashMap<>();

        map.forEach((key, value) -> {
            try {
                final Annotation genAnnotation = value.stream()
                        .filter(a -> a.annotationType().equals(PrimeGen.class))
                        .findAny().orElse(null);

                final IGenerator generator = ((PrimeGen) genAnnotation).value().newInstance();
                generatorsMap.put(key, generator);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.warning(e.getMessage());
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
