package io.dummymaker.factory.impl;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.annotation.collection.GenSet;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.time.GenTime;
import io.dummymaker.container.impl.PopulateContainer;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.gen.*;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.impl.EnumerateScanner;
import io.dummymaker.scan.impl.PopulateEmbeddedFreeScanner;
import io.dummymaker.util.BasicCollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static io.dummymaker.util.BasicCastUtils.castObject;
import static io.dummymaker.util.BasicCastUtils.instantiate;

/**
 * Populate object populate fields using generators and generate factories
 * Scan for populate annotations and generate values for such fields via generators
 *
 * @see IGenerateFactory
 * @see IGenerator
 *
 * @see PrimeGen
 * @see GenEnumerate
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
abstract class BasicPopulateFactory implements IPopulateFactory {

    private static final Logger logger = Logger.getLogger(GenPopulateFactory.class.getName());

    private final IPopulateScanner populateScanner;
    private final IPopulateScanner populateEmbeddedFreeScanner;

    private final IAnnotationScanner enumerateScanner;

    /**
     * Generate factory annotation providers map
     * Indicates which annotation can provide generate factory
     */
    private final Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> generateFactoryProviders;

    BasicPopulateFactory(final IPopulateScanner populateScanner) {
        this.populateEmbeddedFreeScanner = new PopulateEmbeddedFreeScanner();
        this.enumerateScanner = new EnumerateScanner();
        this.populateScanner = populateScanner;

        this.generateFactoryProviders = buildDefaultGenerateFactoryProviders();
    }

    /**
     * To extend generate factory providers with custom ones
     *
     * @param generateFactoryProviders custom generate factory providers
     * @see IGenerateFactory
     */
    BasicPopulateFactory(final IPopulateScanner populateScanner,
                         final Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> generateFactoryProviders) {
        this(populateScanner);
        if (generateFactoryProviders != null) {
            generateFactoryProviders.forEach(this.generateFactoryProviders::put);
        }
    }

    /**
     * Populate single entity
     *
     * @param t                  entity to populate
     * @param enumeratesMap      map of enumerated marked fields
     * @param generatorsMap      map where generator is assigned to each entity field
     * @param generateFactoryMap map with generate factories
     * @param nullableFields        set with fields that had errors in
     * @return populated entity
     */
    private <T> T populateEntity(final T t,
                                 final IPopulateScanner populateScanner,
                                 final Map<Field, IGenerator> generatorsMap,
                                 final Map<Field, IGenerateFactory> generateFactoryMap,
                                 final Map<Field, Long> enumeratesMap,
                                 final Set<Field> nullableFields) {
        if(t == null)
            throw new NullPointerException("Can not populate entity, cause can not instantiate class.");

        final Map<Field, PopulateContainer> populateAnnotationMap = populateScanner.scan(t.getClass());

        for (final Map.Entry<Field, PopulateContainer> annotatedField : populateAnnotationMap.entrySet()) {
            final Field field = annotatedField.getKey();

            // If field had errors in prev populate iteration, just skip that field
            if (nullableFields.contains(field))
                continue;

            try {
                field.setAccessible(true);

                final Object objValue = buildObject(field,
                        annotatedField.getValue(),
                        generatorsMap,
                        generateFactoryMap,
                        enumeratesMap,
                        nullableFields);

                field.set(t, objValue);
            } catch (ClassCastException e) {
                logger.warning(e.getMessage() + " | field TYPE and GENERATE TYPE are not compatible.");
                nullableFields.add(field);
            } catch (IllegalAccessException e) {
                logger.warning(e.getMessage() + " | have NO ACCESS to field.");
                nullableFields.add(field);
            } catch (Exception e) {
                logger.warning(e.getMessage());
                nullableFields.add(field);
            } finally {
                annotatedField.getKey().setAccessible(false);
            }
        }

        return t;
    }

    /**
     * Build field populate value
     *
     * @param field             field to populate
     * @param container field populate annotations
     * @param generatorsMap     fields generators
     * @param enumerateMap      field enumerate map
     * @param nullableFields       set with fields that had errors in
     */
    @SuppressWarnings("unchecked")
    private Object buildObject(final Field field,
                               final PopulateContainer container,
                               final Map<Field, IGenerator> generatorsMap,
                               final Map<Field, IGenerateFactory> generateFactoryMap,
                               final Map<Field, Long> enumerateMap,
                               final Set<Field> nullableFields) {
        final IGenerator generator = generatorsMap.get(field);
        final IGenerateFactory generateFactory = generateFactoryMap.get(field);
        final Annotation fieldAnnotation = container.getGen();

        Object generated;

        if(container.getCore().annotationType().equals(ComplexGen.class)) {
            generated = ((IComplexGenerator) generator).generate(fieldAnnotation, field);
        } else if (generateFactory != null) {
            generated = generateFactory.generate(field, fieldAnnotation, generator);
        } else if (enumerateMap.containsKey(field)) {
            generated = buildNextEnumeratedValue(enumerateMap, field);
        } else if (fieldAnnotation.annotationType().equals(GenEmbedded.class)) {
            generated = buildEmbeddedValue(field, nullableFields);
        } else {
            generated = generator.generate();
        }

        final Object casted = castObject(generated, field.getType());
        if (casted == null) {
            nullableFields.add(field);
            return null;
        }

        return casted;
    }

    /**
     * Build embedded field value
     *
     * @param field       field with embedded value
     * @param errorFields set with fields that had errors in
     */
    private Object buildEmbeddedValue(final Field field,
                                      final Set<Field> errorFields) {
        final Object object = instantiate(field.getType());
        if (object == null) {
            errorFields.add(field);
            return null;
        }

        return populateEntity(instantiate(field.getType()),
                this.populateEmbeddedFreeScanner,
                buildGeneratorsMap(field.getType()),
                buildGenerateFactoryMap(field.getType()),
                buildEnumerateMap(field.getType()),
                new HashSet<>());
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
        } else if(enumeratedField.getType().isAssignableFrom(Double.class)) {
            objValue = Double.valueOf(String.valueOf(objValue));
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
                this.populateScanner,
                buildGeneratorsMap(t.getClass()),
                buildGenerateFactoryMap(t.getClass()),
                buildEnumerateMap(t.getClass()),
                new HashSet<>());
    }

    @Override
    public <T> List<T> populate(final List<T> list) {
        if (BasicCollectionUtils.isEmpty(list))
            return Collections.emptyList();

        final Class<?> tClass = list.get(0).getClass();
        final Set<Field> nullableFields = new HashSet<>();
        final Map<Field, Long> enumerateMap = buildEnumerateMap(tClass);
        final Map<Field, IGenerator> generatorMap = buildGeneratorsMap(tClass);
        final Map<Field, IGenerateFactory> generateFactoryMap = buildGenerateFactoryMap(tClass);

        return list.stream()
                .filter(Objects::nonNull)
                .map(t -> populateEntity(t, this.populateScanner, generatorMap, generateFactoryMap, enumerateMap, nullableFields))
                .collect(Collectors.toList());
    }

    /**
     * Build generate factory map with default generate factory providers
     */
    private Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> buildDefaultGenerateFactoryProviders() {
        final Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> factoryProviders = new HashMap<>();
        factoryProviders.put(GenList.class, ListGenerateFactory.class);
        factoryProviders.put(GenSet.class, SetGenerateFactory.class);
        factoryProviders.put(GenMap.class, MapGenerateFactory.class);
        factoryProviders.put(GenTime.class, TimeGenerateFactory.class);
        return factoryProviders;
    }

    /**
     * Build generators map to improve performance
     * So we initialize generators once for entity and not each populate method call
     */
    private <T> Map<Field, IGenerator> buildGeneratorsMap(final Class<T> tClass) {
        final Map<Field, PopulateContainer> populateAnnotationMap = this.populateScanner.scan(tClass);
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
                        e -> e.getKey(),
                        e -> ((GenEnumerate) e.getValue().get(0)).from())
                );
    }

    /**
     * Setup generate factory map for fields which are annotated with special annotations
     */
    private <T> Map<Field, IGenerateFactory> buildGenerateFactoryMap(final Class<T> tClass) {
        final Map<Field, PopulateContainer> populateAnnotationMap = this.populateScanner.scan(tClass);
        final Map<Field, IGenerateFactory> generateFactoryMap = new HashMap<>();

        populateAnnotationMap.entrySet().stream()
                .filter(e -> generateFactoryProviders.containsKey(e.getValue().getGen().annotationType()))
                .forEach(e -> {

                    final IGenerateFactory generateFactory = instantiate(
                            generateFactoryProviders.get(e.getValue().getGen().annotationType()));

                    if (generateFactory != null) {
                        generateFactoryMap.put(e.getKey(), generateFactory);
                    }
                });

        return generateFactoryMap;
    }

}
