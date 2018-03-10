package io.dummymaker.factory.impl;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.annotation.collection.GenSet;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.time.GenTime;
import io.dummymaker.factory.IGenerateFactory;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.IPopulateScanner;
import io.dummymaker.scan.container.PopulateContainer;
import io.dummymaker.scan.impl.EnumerateScanner;
import io.dummymaker.scan.impl.PopulateEmbeddedFreeScanner;
import io.dummymaker.scan.impl.PopulateScanner;
import io.dummymaker.util.BasicCollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static io.dummymaker.util.BasicCastUtils.*;

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
 * @since 30.05.2017
 */
public class GenPopulateFactory implements IPopulateFactory {

    private static final Logger logger = Logger.getLogger(GenPopulateFactory.class.getName());

    private final IPopulateScanner populateScanner;
    private final IPopulateScanner populateEmbeddedFreeScanner;

    private final IAnnotationScanner enumerateScanner;

    /**
     * Generate factory annotation providers map
     * Indicates which annotation can provide generate factory
     */
    private final Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> generateFactoryProviders;

    public GenPopulateFactory() {
        this.populateScanner = new PopulateScanner();
        this.populateEmbeddedFreeScanner = new PopulateEmbeddedFreeScanner();
        this.enumerateScanner = new EnumerateScanner();

        this.generateFactoryProviders = buildDefaultGenerateFactoryProviders();
    }

    /**
     * To extend generate factory providers with custom ones
     *
     * @param generateFactoryProviders custom generate factory providers
     * @see IGenerateFactory
     */
    public GenPopulateFactory(final Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> generateFactoryProviders) {
        this();
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
     * @param errorFields        set with fields that had errors in
     * @return populated entity
     */
    private <T> T populateEntity(final T t,
                                 final IPopulateScanner populateScanner,
                                 final Map<Field, IGenerator> generatorsMap,
                                 final Map<Field, IGenerateFactory> generateFactoryMap,
                                 final Map<Field, Long> enumeratesMap,
                                 final Set<Field> errorFields) {
        final Map<Field, PopulateContainer> populateAnnotationMap = populateScanner.scan(t.getClass());

        for (final Map.Entry<Field, PopulateContainer> annotatedField : populateAnnotationMap.entrySet()) {
            final Field field = annotatedField.getKey();

            // If field had errors in prev populate iteration, just skip that field
            if (errorFields.contains(field))
                continue;

            try {
                field.setAccessible(true);

                final Object objValue = buildObject(field,
                        annotatedField.getValue(),
                        generatorsMap,
                        generateFactoryMap,
                        enumeratesMap,
                        errorFields);

                field.set(t, objValue);
            } catch (ClassCastException e) {
                logger.warning(e.getMessage() + " | FIELD TYPE AND GENERATE TYPE ARE NOT COMPATIBLE.");
                errorFields.add(field);
            } catch (IllegalAccessException e) {
                logger.warning(e.getMessage() + " | HAVE NO ACCESS TO FIELD.");
                errorFields.add(field);
            } catch (Exception e) {
                logger.warning(e.getMessage());
                errorFields.add(field);
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
     * @param populateContainer field populate annotations
     * @param generatorsMap     fields generators
     * @param enumerateMap      field enumerate map
     * @param errorFields       set with fields that had errors in
     */
    private Object buildObject(final Field field,
                               final PopulateContainer populateContainer,
                               final Map<Field, IGenerator> generatorsMap,
                               final Map<Field, IGenerateFactory> generateFactoryMap,
                               final Map<Field, Long> enumerateMap,
                               final Set<Field> errorFields) {
        final IGenerator generator = generatorsMap.get(field);
        final IGenerateFactory generateFactory = generateFactoryMap.get(field);

        Object generated;

        if (generateFactory != null) {
            generated = generateFactory.generate(field,
                    populateContainer.getGen(),
                    generator);
        } else if (enumerateMap.containsKey(field)) {
            generated = buildNextEnumeratedValue(enumerateMap, field);
        } else if (populateContainer.getGen().annotationType().equals(GenEmbedded.class)) {
            generated = buildEmbeddedValue(field, errorFields);
        } else {
            generated = generator.generate();
        }

        final Object casted = castObject(generated, field.getType());
        if (casted.equals(EMPTY)) {
            errorFields.add(field);
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
        final Object object = instanceClass(field.getType());
        if (object != null) {
            return populateEntity(instanceClass(field.getType()),
                    this.populateEmbeddedFreeScanner,
                    buildGeneratorsMap(field.getType()),
                    buildGenerateFactoryMap(field.getType()),
                    buildEnumerateMap(field.getType()),
                    new HashSet<>());
        } else {
            errorFields.add(field);
            return null;
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

        // Set up map for enumerated fields before population
        final Class<?> tClass = list.get(0).getClass();
        if (tClass == null)
            return Collections.emptyList();

        final Set<Field> errorFields = new HashSet<>();
        final Map<Field, Long> enumerateMap = buildEnumerateMap(tClass);
        final Map<Field, IGenerator> generatorMap = buildGeneratorsMap(tClass);
        final Map<Field, IGenerateFactory> generateFactoryMap = buildGenerateFactoryMap(tClass);

        return list.stream()
                .filter(Objects::nonNull)
                .map(t -> populateEntity(t, this.populateScanner, generatorMap, generateFactoryMap, enumerateMap, errorFields))
                .collect(Collectors.toList());
    }

    /**
     * Build generate factory map with default generate factory providers
     */
    private Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> buildDefaultGenerateFactoryProviders() {
        Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> factoryProviders = new HashMap<>();
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
            final IGenerator generator = instanceClass(((PrimeGen) value.getPrime()).value());
            if (generator != null) {
                generatorsMap.put(key, generator);
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

                    final IGenerateFactory generateFactory = instanceClass(
                            generateFactoryProviders.get(e.getValue().getGen().annotationType()));

                    if (generateFactory != null) {
                        generateFactoryMap.put(e.getKey(), generateFactory);
                    }
                });

        return generateFactoryMap;
    }
}
