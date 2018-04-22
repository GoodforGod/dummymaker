package io.dummymaker.factory.impl;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.impl.EnumerateScanner;
import io.dummymaker.scan.impl.PopulateEmbeddedFreeScanner;
import io.dummymaker.util.BasicCastUtils;
import io.dummymaker.util.BasicCollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static io.dummymaker.util.BasicCastUtils.castObject;
import static io.dummymaker.util.BasicCastUtils.instantiate;

/**
 * Scan for populate annotations on entity fields
 * and generate values for such fields via generators
 *
 * @see IGenerator
 * @see IComplexGenerator
 *
 * @see PrimeGen
 * @see ComplexGen
 *
 * @see GenEnumerate
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
abstract class BasicPopulateFactory implements IPopulateFactory {

    private static final Logger logger = Logger.getLogger(GenPopulateFactory.class.getName());

    private final IAnnotationScanner enumerateScanner;
    private final IPopulateScanner populateScanner;
    private final IPopulateScanner populateEmbeddedFreeScanner;

    BasicPopulateFactory(final IPopulateScanner populateScanner) {
        this.populateEmbeddedFreeScanner = new PopulateEmbeddedFreeScanner();
        this.enumerateScanner = new EnumerateScanner();
        this.populateScanner = populateScanner;
    }

    /**
     * Populate single entity
     *
     * @param t                  entity to populate
     * @param enumeratesMap      map of enumerated marked fields
     * @param generatorsMap      map where generator is assigned to each entity field
     * @param nullableFields     set with fields that had errors in
     * @return populated entity
     */
    private <T> T populateEntity(final T t,
                                 final IPopulateScanner populateScanner,
                                 final Map<Field, IGenerator> generatorsMap,
                                 final Map<Field, Long> enumeratesMap,
                                 final Set<Field> nullableFields) {
        final Map<Field, GenContainer> populateAnnotationMap = populateScanner.scan(t.getClass());

        for (final Map.Entry<Field, GenContainer> annotatedField : populateAnnotationMap.entrySet()) {
            final Field field = annotatedField.getKey();

            // If field had errors or null gen in prev populate iteration, just skip that field
            if (nullableFields.contains(field))
                continue;

            try {
                field.setAccessible(true);

                final Object objValue = generateObject(field,
                        annotatedField.getValue(),
                        generatorsMap,
                        enumeratesMap,
                        nullableFields);

                field.set(t, objValue);

            } catch (ClassCastException e) {
                logger.warning(e.getMessage() + " | field TYPE and GENERATE TYPE are not compatible.");
                nullableFields.add(field); // skip field due to error as if it null
            } catch (IllegalAccessException e) {
                logger.warning(e.getMessage() + " | have NO ACCESS to field.");
                nullableFields.add(field); // skip field due to error as if it null
            } catch (Exception e) {
                logger.warning(e.getMessage());
                nullableFields.add(field); // skip field due to error as if it null
            } finally {
                annotatedField.getKey().setAccessible(false);
            }
        }

        return t;
    }

    /**
     * Generate populate field value
     *
     * @param field             field to populate
     * @param container field populate annotations
     * @param generatorsMap     fields generators
     * @param enumerateMap      field enumerate map
     * @param nullableFields       set with fields that had errors in
     */
    private Object generateObject(final Field field,
                                  final GenContainer container,
                                  final Map<Field, IGenerator> generatorsMap,
                                  final Map<Field, Long> enumerateMap,
                                  final Set<Field> nullableFields) {
        final IGenerator generator = generatorsMap.get(field);
        final Annotation fieldAnnotation = container.getGen();

        Object generated;

        if (enumerateMap.containsKey(field)) {
            generated = generateEnumerateObject(field, enumerateMap);
        } else if (fieldAnnotation.annotationType().equals(GenEmbedded.class)) {
            generated = generateEmbeddedObject(field, nullableFields);
        } else if(container.getCore().annotationType().equals(ComplexGen.class)) {
            generated = ((IComplexGenerator) generator).generate(fieldAnnotation, field);
        }  else {
            generated = generator.generate();
        }

        final Object casted = castObject(generated, field.getType());
        if (casted == null)
            nullableFields.add(field);

        return casted;
    }

    /**
     * Generate embedded field value
     *
     * @param field       field with embedded value
     * @param nullableFields set with fields that had errors in
     */
    private Object generateEmbeddedObject(final Field field,
                                          final Set<Field> nullableFields) {
        final Object embedded = instantiate(field.getType());
        if(embedded == null) {
            nullableFields.add(field);
            return null;
        }

        return populateEntity(embedded,
                this.populateEmbeddedFreeScanner,
                buildGeneratorsMap(field.getType()),
                buildEnumerateMap(field.getType()),
                new HashSet<>());
    }

    /**
     * Generate enumerate field next value
     */
    private Object generateEnumerateObject(final Field field,
                                           final Map<Field, Long> enumerateMap) {
        final Long currentEnumerateValue = enumerateMap.get(field);
        Object objValue = BasicCastUtils.castToNumber(currentEnumerateValue, field.getType());

        // Increment numerate number for generated field
        enumerateMap.computeIfPresent(field, (k, v) -> v + 1);
        return objValue;
    }


    @Override
    public <T> T populate(final T t) {
        if (t == null)
            return null;

        return populateEntity(t,
                this.populateScanner,
                buildGeneratorsMap(t.getClass()),
                buildEnumerateMap(t.getClass()),
                new HashSet<>());
    }

    @Override
    public <T> List<T> populate(final List<T> list) {
        if (BasicCollectionUtils.isEmpty(list))
            return Collections.emptyList();

        final Class<?> tClass = list.get(0).getClass();
        final Set<Field> nullableFields = new HashSet<>(); // use for performance
        final Map<Field, Long> enumerateMap = buildEnumerateMap(tClass); // store enumerate gen state
        final Map<Field, IGenerator> generatorMap = buildGeneratorsMap(tClass); // use for performance

        return list.stream()
                .filter(Objects::nonNull)
                .map(t -> populateEntity(t, this.populateScanner, generatorMap, enumerateMap, nullableFields))
                .collect(Collectors.toList());
    }


    /**
     * Build generators map to improve performance
     * So we initialize generators once for entity and not each populate method call
     */
    private <T> Map<Field, IGenerator> buildGeneratorsMap(final Class<T> tClass) {
        final Map<Field, GenContainer> populateAnnotationMap = this.populateScanner.scan(tClass);
        final Map<Field, IGenerator> generatorsMap = new HashMap<>();

        populateAnnotationMap.forEach((key, value) -> {
            if(value.getCore().annotationType().equals(PrimeGen.class)) {
                final IGenerator generator = instantiate(((PrimeGen) value.getCore()).value());
                if (generator != null) {
                    generatorsMap.put(key, generator);
                }
            } else if(value.getCore().annotationType().equals(ComplexGen.class)) {
                final IComplexGenerator generator = instantiate(((ComplexGen) value.getCore()).value());
                if (generator != null) {
                    generatorsMap.put(key, generator);
                }
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
        return this.enumerateScanner.scan(t).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> ((GenEnumerate) e.getValue().get(0)).from())
                );
    }
}
