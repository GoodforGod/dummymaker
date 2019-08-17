package io.dummymaker.scan.impl;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.annotation.special.GenIgnore;
import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.factory.impl.GenSupplier;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.model.GenContainer;
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

    private static final Predicate<Annotation> IS_AUTO = (a) -> a.annotationType().equals(GenAuto.class);

    private final Predicate<Annotation> isGenCustom = (a) -> a.annotationType().equals(GenCustom.class);
    private final Predicate<Annotation> isIgnored = (a) -> a.annotationType().equals(GenIgnore.class);
    private final Predicate<Annotation> isGen = (a) -> a.annotationType().equals(PrimeGen.class)
            || a.annotationType().equals(ComplexGen.class);

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
    public Map<Field, GenContainer> scan(Class target, boolean defAuto) {
        final Map<Field, GenContainer> containers = new LinkedHashMap<>();
        final boolean isGenAuto = isMarkedGenAuto(target, defAuto);

        getAllDeclaredFields(target).stream()
                .filter(f -> !isIgnored(f))
                .forEach(f -> getContainer(f, isGenAuto).ifPresent(c -> containers.put(f, c)));

        return containers;
    }

    @Override
    public Map<Field, GenContainer> scan(final Class target) {
        return scan(target, false);
    }

    /**
     * Check if class is auto generative
     * @param target to check for annotation
     * @see GenAuto
     * @return optional gen auto annotation
     */
    private static boolean isMarkedGenAuto(Class<?> target, boolean defAuto) {
        final boolean isMarked = (target != null) && Arrays.stream(target.getDeclaredAnnotations()).anyMatch(IS_AUTO);
        return isMarked || defAuto;
    }

    public static Optional<Annotation> getAutoAnnotation(Class<?> target) {
        return (target == null)
                ? Optional.empty()
                : Arrays.stream(target.getDeclaredAnnotations())
                .filter(IS_AUTO)
                .findAny();
    }

    /**
     * Check if the field have complex suitable generator
     * @param field to check against
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
     *
     * @param field   target to containerize
     * @param isGenAuto is marked with gen auto annotation
     * @return gen container as gen auto
     */
    private Optional<GenContainer> getAutoContainer(Field field, boolean isGenAuto) {
        if (!isGenAuto)
            return Optional.empty();

        final boolean isComplex = isComplex(field);
        final GenContainer container = GenContainer.asAuto(isComplex);
        final Class<? extends IGenerator> suitable = supplier.getSuitable(field);
        container.enrich(suitable);
        return Optional.of(container);
    }

    /**
     * Found gen or custom gen annotated fields and wraps them into containers
     * <p>
     * If not presented than try to generate gen auto container
     *
     * @param field   target to containerize
     * @param isGenAuto gen auto annotation
     * @return gen container
     * @see #getAutoContainer(Field, boolean)
     */
    private Optional<GenContainer> getContainer(Field field, boolean isGenAuto) {
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

        return getAutoContainer(field, isGenAuto);
    }

    /**
     * Exclude ignored population fields
     */
    private boolean isIgnored(final Field field) {
        return Arrays.stream(field.getDeclaredAnnotations()).anyMatch(isIgnored);
    }
}
