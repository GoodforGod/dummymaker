package io.dummymaker.scan.impl;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.annotation.special.GenIgnore;
import io.dummymaker.model.GenContainer;
import io.dummymaker.scan.IGenScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

/**
 * Scanner used by populate factory Scan for prime gen annotation and its child
 * annotation
 *
 * @author GoodforGod
 * @see PrimeGen
 * @see ComplexGen
 * @since 29.05.2017
 */
public class GenScanner extends BasicScanner implements IGenScanner {

    private final Predicate<Annotation> isGenCustom = a -> GenCustom.class.equals(a.annotationType());
    private final Predicate<Annotation> isIgnored = a -> GenIgnore.class.equals(a.annotationType());
    private final Predicate<Annotation> isGen = a -> PrimeGen.class.equals(a.annotationType())
            || ComplexGen.class.equals(a.annotationType());

    /**
     * Scan for prime/complex gen annotation and its child annotation
     *
     * @param target class to scan KEY is field that has populate annotations VALUE
     *               is Gen container with generate params for that field
     * @return populate field map, where
     * @see GenContainer
     */
    @Override
    public Map<Field, GenContainer> scan(final Class target) {
        final Map<Field, GenContainer> containers = new LinkedHashMap<>();

        getAllFilteredFields(target).stream()
                .filter(f -> !isIgnored(f))
                .forEach(f -> getContainer(f).ifPresent(c -> containers.put(f, c)));

        return containers;
    }

    /**
     * Check if the field have complex suitable generator
     *
     * @param field to check against
     * @return true if field is complex
     */
    protected boolean isComplex(final Field field) {
        final Class<?> declaringClass = field.getType();
        return (List.class.isAssignableFrom(declaringClass)
                || Set.class.isAssignableFrom(declaringClass)
                || Map.class.isAssignableFrom(declaringClass))
                || declaringClass.getTypeName().endsWith("[][]")
                || declaringClass.getTypeName().endsWith("[]")
                || declaringClass.isEnum();
    }

    /**
     * Found gen or custom gen annotated fields and wraps them into containers
     * <p>
     * If not presented than try to generate gen auto container
     *
     * @param field target to containerize
     * @return gen container
     */
    private Optional<GenContainer> getContainer(Field field) {
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (isGenCustom.test(annotation)) {
                return Optional.of(GenContainer.asCustom(field, annotation));
            }

            for (Annotation inline : annotation.annotationType().getDeclaredAnnotations()) {
                if (isGen.test(inline)) {
                    return Optional.of(GenContainer.asGen(field, inline, annotation));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Exclude ignored population fields
     *
     * @param field to validate
     * @return true if fields is ignored
     */
    protected boolean isIgnored(final Field field) {
        return Arrays.stream(field.getDeclaredAnnotations()).anyMatch(isIgnored);
    }
}
