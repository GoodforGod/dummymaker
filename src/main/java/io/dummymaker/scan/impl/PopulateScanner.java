package io.dummymaker.scan.impl;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.special.GenAuto;
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

import static io.dummymaker.util.BasicGenUtils.getAutoGenerator;

/**
 * Scanner used by populate factory
 * <p>
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
     */
    private final Predicate<Annotation> isGen = (a) -> a.annotationType().equals(PrimeGen.class)
            || a.annotationType().equals(ComplexGen.class);

    /**
     * Scan for prime/complex gen annotation and its child annotation
     *
     * @param t class to scan
     * @return populate field map, where
     * KEY is field that has populate annotations
     * VALUE is Gen container with generate params for that field
     * @see GenContainer
     */
    @Override
    public Map<Field, GenContainer> scan(final Class t) {
        final Map<Field, GenContainer> populateAnnotationMap = new LinkedHashMap<>();

        // Check if class is auto generative
        final boolean isAutoGen = Arrays.stream(t.getDeclaredAnnotations())
                .anyMatch(a -> a.annotationType().equals(GenAuto.class));

        for (final Field field : getAllDeclaredFields(t)) {
            GenContainer genContainer = findGenAnnotation(field);

            // Create auto gen container class is auto generative
            if (genContainer == null && isAutoGen) {
                Class<? extends IGenerator> generator = getAutoGenerator(field.getType());
                if (generator.equals(NullGenerator.class)) {
                    // Try to treat field as embedded object, when no suitable generator
                    generator = EmbeddedGenerator.class;
                }

                genContainer = GenContainer.asAuto(generator, isComplex(field));
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
        return (declaringClass.equals(List.class)
                || declaringClass.equals(Set.class)
                || declaringClass.equals(Map.class))
                || declaringClass.getTypeName().endsWith("[][]")
                || declaringClass.getTypeName().endsWith("[]")
                || declaringClass.isEnum();
    }

    /**
     * Found only first found gen annotation on field
     */
    private GenContainer findGenAnnotation(final Field field) {
        for (Annotation annotation : field.getDeclaredAnnotations()) {
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
        return Arrays.stream(field.getDeclaredAnnotations()).anyMatch(a -> a.annotationType().equals(GenIgnore.class));
    }
}
