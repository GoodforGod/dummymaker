package io.dummymaker;

import io.dummymaker.annotation.*;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.util.CastUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 18.11.2022
 */
final class GenScanner {

    private static final Predicate<Annotation> IS_AUTO = a -> GenAuto.class.equals(a.annotationType());
    private static final Predicate<Annotation> IS_GEN = a -> GenCustom.class.equals(a.annotationType());
    private static final Predicate<Annotation> IS_FACTORY = a -> GenCustomFactory.class.equals(a.annotationType());

    private final GeneratorSupplier generatorSupplier;
    private final GenRules rules;
    private final boolean isAutoByDefault;
    private final int depthByDefault;

    GenScanner(GeneratorSupplier generatorSupplier, GenRules rules, boolean isAutoByDefault, int depthByDefault) {
        this.generatorSupplier = generatorSupplier;
        this.rules = rules;
        this.isAutoByDefault = isAutoByDefault;
        this.depthByDefault = depthByDefault;
    }

    public @NotNull List<GenContainer> scan(GenType target) {
        // TODO scan generic type parameters for target
        final Class<?> targetType = target.raw();
        final List<Field> validFields = getValidFields(targetType);
        final Set<String> ignored = rules.find(targetType)
                .map(GenRule::getIgnored)
                .orElse(Collections.emptySet());

        final List<Field> fields = validFields.stream()
                .filter(f -> !f.isAnnotationPresent(GenIgnore.class))
                .filter(f -> !ignored.contains(f.getName()))
                .collect(Collectors.toList());

        return fields.stream()
                .map(f -> getContainer(targetType, f).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Optional<GenContainer> getContainer(Class<?> target, Field field) {
        final Optional<GenRule> rule = rules.find(target);
        final int depth = rule.flatMap(GenRule::getDepth)
                .orElseGet(() -> {
                    final GenDepth annotation = field.getAnnotation(GenDepth.class);
                    final int expectedDepth = (annotation != null)
                            ? annotation.value()
                            : Arrays.stream(target.getDeclaredAnnotations())
                                    .filter(a -> a instanceof GenDepth)
                                    .map(a -> ((GenDepth) a).value())
                                    .findAny()
                                    .orElse(depthByDefault);

                    if (expectedDepth < 1 || expectedDepth > GenDepth.MAX) {
                        throw new IllegalArgumentException(
                                "Depth must be between 1 and 50, but was " + expectedDepth + " for " + target);
                    }

                    return expectedDepth;
                });

        final Optional<Generator<?>> ruleGenerator = rule.flatMap(r -> r.find(field));
        if (ruleGenerator.isPresent()) {
            final boolean isComplex = isComplex(field);
            return Optional.of(GenContainer.ofRule(field, ruleGenerator.get(), depth, isComplex));
        }

        for (Annotation marker : field.getDeclaredAnnotations()) {
            if (IS_AUTO.test(marker)) {
                final Generator<?> generator = generatorSupplier.get(field);
                final boolean isComplex = isComplex(field);
                return Optional.of(GenContainer.ofAuto(field, generator, depth, isComplex, marker));
            }

            if (IS_FACTORY.test(marker)) {
                final AnnotationGeneratorFactory generatorFactory = CastUtils
                        .instantiate(((GenCustomFactory) marker).value());
                final Generator<?> generator = generatorFactory.get(marker);
                final boolean isComplex = isComplex(field);
                return Optional.of(GenContainer.ofMarker(field, generator, depth, isComplex, marker));
            }

            if (IS_GEN.test(marker)) {
                final Generator<?> generator = CastUtils.instantiate(((GenCustom) marker).value());
                final boolean isComplex = isComplex(field);
                return Optional.of(GenContainer.ofMarker(field, generator, depth, isComplex, marker));
            }

            for (Annotation custom : marker.annotationType().getDeclaredAnnotations()) {
                if (IS_FACTORY.test(custom)) {
                    final AnnotationGeneratorFactory generatorFactory = CastUtils
                            .instantiate(((GenCustomFactory) custom).value());
                    final Generator<?> generator = generatorFactory.get(marker);
                    final boolean isComplex = isComplex(field);
                    return Optional.of(GenContainer.ofMarker(field, generator, depth, isComplex, marker));
                }

                if (IS_GEN.test(custom)) {
                    final Generator<?> generator = CastUtils.instantiate(((GenCustom) custom).value());
                    final boolean isComplex = isComplex(field);
                    return Optional.of(GenContainer.ofMarker(field, generator, depth, isComplex, marker));
                }
            }
        }

        if (isAutoByDefault || rule.flatMap(GenRule::isAuto).orElse(false)) {
            final Generator<?> generator = generatorSupplier.get(field);
            final boolean isComplex = isComplex(field);
            return Optional.of(GenContainer.ofAuto(field, generator, depth, isComplex, null));
        }

        return Optional.empty();
    }

    private static boolean isComplex(Field field) {
        final Class<?> declaringClass = field.getType();
        return (List.class.isAssignableFrom(declaringClass)
                || Set.class.isAssignableFrom(declaringClass)
                || Map.class.isAssignableFrom(declaringClass))
                || declaringClass.getTypeName().endsWith("[][]")
                || declaringClass.getTypeName().endsWith("[]")
                || declaringClass.isEnum();
    }

    @NotNull
    private List<Field> getValidFields(Class<?> target) {
        return getAllFields(target).stream()
                .filter(f -> !f.isSynthetic())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isNative(f.getModifiers()))
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .filter(f -> !Modifier.isSynchronized(f.getModifiers()))
                .collect(Collectors.toList());
    }

    @NotNull
    private List<Field> getAllFields(Class<?> target) {
        if (target == null || Object.class.equals(target)) {
            return Collections.emptyList();
        }

        final List<Field> superFields = getValidFields(target.getSuperclass());
        if (superFields.isEmpty()) {
            return Arrays.asList(target.getDeclaredFields());
        } else {
            final List<Field> targetFields = Arrays.asList(target.getDeclaredFields());
            superFields.addAll(targetFields);
            return superFields;
        }
    }
}
