package io.dummymaker.scan.impl;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.annotation.special.GenIgnore;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.scan.IPopulateScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

import static io.dummymaker.util.GenUtils.getAutoGenerator;

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
        final Map<Field, GenContainer> populateAnnotationMap = new LinkedHashMap<>();

        // Check if class is auto generative
        final Optional<Annotation> genAuto = Arrays.stream(target.getDeclaredAnnotations())
                .filter(isAuto)
                .findAny();

        for (final Field field : getAllDeclaredFields(target)) {
            GenContainer genContainer = findGenAnnotation(field);

            // Create auto gen container class is auto generative
            if (genContainer == null && genAuto.isPresent()) {
                Class<? extends IGenerator> generator = getAutoGenerator(field.getType());
                if (generator.equals(NullGenerator.class)) {
                    // Try to treat field as embedded object, when no suitable generator
                    generator = EmbeddedGenerator.class;
                }

                final int autoDepth = genAuto.map(a -> ((GenAuto) a).depth()).orElse(1);
                genContainer = GenContainer.asAuto(generator, isComplex(field), autoDepth);
            }

            if (!isIgnored(field) && genContainer != null) {
                populateAnnotationMap.put(field, genContainer);
            }
        }

        return populateAnnotationMap;
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
     * Found gen or custom gen annotated fields and wraps them into containers
     */
    private GenContainer findGenAnnotation(final Field field) {
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (isGenCustom.test(annotation)) {
                return GenContainer.asCustom(annotation);
            }

            for (Annotation inline : annotation.annotationType().getDeclaredAnnotations()) {
                if (isGen.test(inline)) {
                    return GenContainer.asGen(inline, annotation);
                }
            }
        }

        return null;
    }

    /**
     * Exclude ignored population fields
     */
    private boolean isIgnored(final Field field) {
        return Arrays.stream(field.getDeclaredAnnotations()).anyMatch(isIgnored);
    }
}
