package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.annotation.*;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.EmbeddedGenerator;
import io.goodforgod.dummymaker.util.CastUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 18.11.2022
 */
final class GenFieldScanner {

    private static final Predicate<Annotation> IS_AUTO = a -> GenAuto.class.equals(a.annotationType());
    private static final Predicate<Annotation> IS_GEN = a -> GenCustom.class.equals(a.annotationType());
    private static final Predicate<Annotation> IS_FACTORY = a -> GenCustomFactory.class.equals(a.annotationType());

    private final GeneratorSupplier generatorSupplier;
    private final GenRulesContext rules;
    private final boolean isAutoByDefault;

    GenFieldScanner(GeneratorSupplier generatorSupplier, GenRulesContext rules, boolean isAutoByDefault) {
        this.generatorSupplier = generatorSupplier;
        this.rules = rules;
        this.isAutoByDefault = isAutoByDefault;
    }

    boolean isEmbedded(@NotNull GenType parent, @NotNull GenType target) {
        final Generator<?> generator = rules.findClass(parent)
                .flatMap(rule -> rule.find(target))
                .orElseGet(() -> generatorSupplier.get(target.raw()));

        return generator instanceof EmbeddedGenerator;
    }

    @NotNull
    List<GenField> scan(@NotNull GenType target) {
        final Class<?> targetType = target.raw();
        final List<ScanField> validFields = getValidFields(targetType);
        final Set<String> ignored = rules.findClass(targetType)
                .map(GenRuleContext::getExcludedFields)
                .orElse(Collections.emptySet());

        final List<ScanField> fields = validFields.stream()
                .filter(field -> !field.value().isAnnotationPresent(GenIgnore.class))
                .filter(field -> !ignored.contains(field.value().getName()))
                .collect(Collectors.toList());

        return fields.stream()
                .map(field -> convertToGenField(targetType, field).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Optional<GenField> convertToGenField(Class<?> target, ScanField field) {
        final boolean isComplex = isComplex(field.type());
        final Optional<GenRuleContext> rule = rules.findClass(target);
        final Integer depth = rule.flatMap(GenRuleContext::getDepth)
                .orElseGet(() -> {
                    final GenDepth annotation = field.value().getAnnotation(GenDepth.class);
                    final Integer expectedDepth = (annotation != null)
                            ? Integer.valueOf(annotation.value())
                            : Arrays.stream(target.getDeclaredAnnotations())
                                    .filter(a -> a instanceof GenDepth)
                                    .map(a -> ((GenDepth) a).value())
                                    .findFirst()
                                    .orElse(null);

                    if (expectedDepth != null && (expectedDepth < 1 || expectedDepth > GenDepth.MAX)) {
                        throw new IllegalArgumentException(
                                "Depth must be between 1 and " + GenDepth.MAX + ", but was " + expectedDepth + " for " + target);
                    }

                    return expectedDepth;
                });

        final String fieldName = field.value().getName();
        final Class<?> fieldType = field.type().raw();
        final Optional<Generator<?>> ruleGenerator = rule.flatMap(r -> r.find(fieldType, fieldName));
        if (ruleGenerator.isPresent()) {
            return Optional.of(GenField.ofRule(field.value(), field.type(), ruleGenerator.get(), isComplex, depth));
        }

        for (Annotation marker : field.value().getDeclaredAnnotations()) {
            if (IS_AUTO.test(marker)) {
                final Generator<?> generator = generatorSupplier.get(fieldType, fieldName);
                return Optional.of(GenField.ofAuto(field.value(), field.type(), generator, isComplex, depth));
            }

            if (IS_GEN.test(marker)) {
                final Generator<?> generator = CastUtils.instantiate(((GenCustom) marker).value());
                return Optional.of(GenField.ofMarker(field.value(), field.type(), generator, isComplex, depth));
            }

            for (Annotation custom : marker.annotationType().getDeclaredAnnotations()) {
                if (IS_FACTORY.test(custom)) {
                    final AnnotationGeneratorFactory generatorFactory = CastUtils
                            .instantiate(((GenCustomFactory) custom).value());
                    final Generator<?> generator = generatorFactory.get(marker);
                    return Optional.of(GenField.ofMarker(field.value(), field.type(), generator, isComplex, depth));
                }

                if (IS_GEN.test(custom)) {
                    final Generator<?> generator = CastUtils.instantiate(((GenCustom) custom).value());
                    return Optional.of(GenField.ofMarker(field.value(), field.type(), generator, isComplex, depth));
                }
            }
        }

        if (rule.flatMap(GenRuleContext::isAuto).orElse(isAutoByDefault)) {
            final Generator<?> generator = generatorSupplier.get(fieldType, fieldName);
            if (generator instanceof EmbeddedGenerator) {
                final Generator<?> permittedGenerator = rule.flatMap(r -> r.find(field.type().raw(), fieldName))
                        .orElseGet(() -> generatorSupplier.get(field.type().raw(), fieldName));
                return Optional.of(GenField.ofAuto(field.value(), field.type(), permittedGenerator, isComplex, depth));
            }

            return Optional.of(GenField.ofAuto(field.value(), field.type(), generator, isComplex, depth));
        }

        return Optional.empty();
    }

    private static boolean isComplex(GenType type) {
        final Class<?> declaringClass = type.raw();
        return Collection.class.isAssignableFrom(declaringClass)
                || List.class.isAssignableFrom(declaringClass)
                || Set.class.isAssignableFrom(declaringClass)
                || Map.class.isAssignableFrom(declaringClass)
                || declaringClass.getTypeName().endsWith("[][]")
                || declaringClass.getTypeName().endsWith("[]")
                || declaringClass.isEnum();
    }

    private static class ScanField {

        private final Field value;
        private final GenType type;

        private ScanField(Field value, GenType type) {
            this.value = value;
            this.type = type;
        }

        public Field value() {
            return value;
        }

        public GenType type() {
            return type;
        }
    }

    @NotNull
    private List<ScanField> getValidFields(Type target) {
        if (target == null || Object.class.equals(target)) {
            return Collections.emptyList();
        }

        final Class<?> targetClass = (target instanceof ParameterizedType)
                ? (Class<?>) ((ParameterizedType) target).getRawType()
                : ((Class<?>) target);

        final List<ScanField> superFields = getValidFields(targetClass.getGenericSuperclass());
        final List<ScanField> targetFields = Arrays.stream(targetClass.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isNative(f.getModifiers()))
                .filter(f -> !Modifier.isSynchronized(f.getModifiers()))
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .flatMap(f -> DefaultGenType.ofType(f.getGenericType())
                        .map(v -> Stream.of(new ScanField(f, v)))
                        .orElse(Stream.empty()))
                .collect(Collectors.toList());

        if (superFields.isEmpty()) {
            return targetFields;
        } else {
            superFields.addAll(targetFields);
            return superFields;
        }
    }
}
