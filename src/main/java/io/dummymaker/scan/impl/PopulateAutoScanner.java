package io.dummymaker.scan.impl;

import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.model.GenContainer;
import io.dummymaker.scan.IPopulateAutoScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Implementation of scanner that suits gen auto annotations where needed
 *
 * @author GoodforGod
 * @see PopulateScanner
 * @see GenAuto
 * @since 18.08.2019
 */
public class PopulateAutoScanner extends PopulateScanner implements IPopulateAutoScanner {

    private static final Predicate<Annotation> IS_AUTO = (a) -> a.annotationType().equals(GenAuto.class);

    private final IGenSupplier supplier;

    public PopulateAutoScanner(IGenSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public Map<Field, GenContainer> scan(Class target) {
        return scan(target, false);
    }

    @Override
    public Map<Field, GenContainer> scan(Class target, boolean isDefaultAuto) {
        final Map<Field, GenContainer> scanned = super.scan(target);

        final boolean isGenAuto = isMarkedGenAuto(target, isDefaultAuto);
        if(!isGenAuto)
            return scanned;

        final Map<Field, GenContainer> containers = new LinkedHashMap<>();
        getAllDeclaredFields(target).stream()
                .filter(f -> !isIgnored(f))
                .forEach(f -> containers.put(f, scanned.getOrDefault(f, getAutoContainer(f))));

        return containers;
    }

    /**
     * Create auto gen container
     *
     * @param field target to containerize
     * @return gen auto container
     */
    private GenContainer getAutoContainer(Field field) {
        final boolean isComplex = isComplex(field);
        final Class<? extends IGenerator> suitable = supplier.getSuitable(field);
        return GenContainer.asAuto(suitable, isComplex);
    }

    /**
     * Check if class is auto generative
     *
     * @param target to check for annotation
     * @return true if marked
     */
    private static boolean isMarkedGenAuto(Class<?> target, boolean isDefaultAuto) {
        final boolean isMarked = getAutoAnnotation(target).isPresent();
        return isMarked || isDefaultAuto;
    }

    /**
     * Finds target gen auto annotation if presents
     *
     * @param target to check for annotation
     * @return gen auto annotation
     * @see GenAuto
     */
    public static Optional<Annotation> getAutoAnnotation(Class<?> target) {
        return (target == null)
                ? Optional.empty()
                : Arrays.stream(target.getDeclaredAnnotations())
                .filter(IS_AUTO)
                .findAny();
    }
}
