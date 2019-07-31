package io.dummymaker.scan.impl;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.annotation.special.GenIgnore;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.factory.impl.GenSupplier;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.scan.IPopulateScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

/**
 * Scanner used by populate factory
 * Scan for prime gen annotation and its child annotation
 * Scan also for GenEmbedded annotations to populate embedded fields
 *
 * @author GoodforGod
 * @see PrimeGen
 * @see GenEmbedded
 * @see AnnotationScanner
 * @see io.dummymaker.factory.IPopulateFactory
 * @since 29.05.2017
 */
public class PopulateScanner extends BasicScanner implements IPopulateScanner {

    /**
     * Predicate to check for core prime/complex marker annotation
     *
     * @see PrimeGen
     * @see ComplexGen
     */
    private final Predicate<Annotation> isGen = (a) -> a.annotationType().equals(PrimeGen.class)
            || a.annotationType().equals(ComplexGen.class);

    private final Predicate<Annotation> isGenCustom = (a) -> a.annotationType().equals(GenCustom.class);
    private final Predicate<Annotation> isIgnored = (a) -> a.annotationType().equals(GenIgnore.class);
    private final Predicate<Annotation> isAuto = (a) -> a.annotationType().equals(GenAuto.class);

    private final IGenSupplier supplier = new GenSupplier();

    /**
     * Scan for prime/complex gen annotation and its child annotation
     *
     * @param target class to scan
     *               KEY is field that has populate annotations
     *               VALUE is Gen container with generate params for that field
     * @return populate field map, where
     * @see GenContainer
     */
    @Override
    public Map<Field, GenContainer> scan(final Class target) {
        final Map<Field, GenContainer> containerMap = new LinkedHashMap<>();

        // Check if class is auto generative
        final Annotation genAuto = Arrays.stream(target.getDeclaredAnnotations())
                .filter(isAuto)
                .findAny().orElse(null);

        getAllDeclaredFields(target).stream()
                .filter(f -> !isIgnored(f))
                .forEach(f -> getContainer(f, genAuto).ifPresent(c -> containerMap.put(f, c)));

        return containerMap;
    }

    /**
     * Check if the field have complex suitable generator
     */
    private boolean isComplex(final Field field) {
        final Class<?> declaringClass = field.getType();
        return (declaringClass.isAssignableFrom(List.class)
                || declaringClass.isAssignableFrom(Set.class)
                || declaringClass.isAssignableFrom(Map.class))
                || declaringClass.getTypeName().endsWith("[][]")
                || declaringClass.getTypeName().endsWith("[]")
                || declaringClass.isEnum();
    }

    /**
     * Create auto gen container class is auto generative
     * @param field target to containerize
     * @param genAuto gen auto annotation
     * @return gen container as gen auto
     */
    private Optional<GenContainer> getAutoContainer(Field field, Annotation genAuto) {
        if(genAuto == null)
            return Optional.empty();

        final int depth = EmbeddedGenerator.toDepth(((GenAuto) genAuto).depth());
        final GenContainer container = GenContainer.asAuto(isComplex(field), depth);
        final Class<? extends IGenerator> suitable = supplier.getSuitable(field);
        container.enrich(suitable);
        return Optional.of(container);
    }

    /**
     * Found gen or custom gen annotated fields and wraps them into containers
     *
     * If not presented than try to generate gen auto container
     * @see #getAutoContainer(Field, Annotation)
     *
     * @param field target to containerize
     * @param genAuto gen auto annotation
     * @return gen container
     */
    private Optional<GenContainer> getContainer(Field field, Annotation genAuto) {
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (isGenCustom.test(annotation)) {
                return Optional.of(GenContainer.asCustom(annotation));
            }

            for (Annotation inline : annotation.annotationType().getDeclaredAnnotations()) {
                if (isGen.test(inline)) {
                    return Optional.of(GenContainer.asGen(inline, annotation));
                }
            }
        }

        return getAutoContainer(field, genAuto);
    }

    /**
     * Exclude ignored population fields
     */
    private boolean isIgnored(final Field field) {
        return Arrays.stream(field.getDeclaredAnnotations()).anyMatch(isIgnored);
    }
}
