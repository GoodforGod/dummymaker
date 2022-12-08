package io.dummymaker.scan.impl;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.factory.GenSupplier;
import io.dummymaker.factory.refactored.GeneratorSupplier;
import io.dummymaker.generator.Generator;
import io.dummymaker.model.GenContainer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.dummymaker.scan.GenAutoScanner;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of scanner that suits gen auto annotations where needed
 *
 * @author Anton Kurako (GoodforGod)
 * @see MainGenScanner
 * @see GenAuto
 * @since 18.08.2019
 */
public class MainGenAutoScanner extends MainGenScanner implements GenAutoScanner {

    private static final Predicate<Annotation> IS_AUTO = a -> GenAuto.class.equals(a.annotationType());

    private final GenSupplier supplier;

    public MainGenAutoScanner(GenSupplier supplier) {
        this.supplier = supplier;
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

    @Override
    public @NotNull List<GenContainer> scan(Class<?> target) {
        return scan(target, false);
    }

    @Override
    public @NotNull List<GenContainer> scan(Class<?> target, boolean isDefaultAuto) {
        final List<GenContainer> scanned = super.scan(target);
        final boolean isGenAuto = isMarkedGenAuto(target, isDefaultAuto);
        if (!isGenAuto) {
            return scanned;
        }

        final Map<Field, GenContainer> scannedMap = scanned.stream()
                .collect(Collectors.toMap(GenContainer::getField, c -> c, (c1,c2) -> c2, LinkedHashMap::new));

        final List<Field> fields = getValidFields(target);
        final Map<Field, GenContainer> containers = new LinkedHashMap<>(fields.size());
        fields.stream()
                .filter(field -> !isIgnored(field))
                .forEach(field -> containers.put(field, scannedMap.computeIfAbsent(field, k -> getAutoContainer(field))));

        return new ArrayList<>(containers.values());
    }

    /**
     * Create auto gen container
     *
     * @param field target to containerize
     * @return gen auto container
     */
    private GenContainer getAutoContainer(Field field) {
        final boolean isComplex = isComplex(field);
        final Generator<?> suitable = supplier.getSuitable(field);
        return GenContainer.ofAuto(field, suitable, isComplex);
    }
}
