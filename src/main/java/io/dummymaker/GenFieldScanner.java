package io.dummymaker;

import io.dummymaker.annotation.*;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.util.CastUtils;
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
    private final GenRules rules;
    private final boolean isAutoByDefault;
    private final int depthByDefault;

    GenFieldScanner(GeneratorSupplier generatorSupplier, GenRules rules, boolean isAutoByDefault, int depthByDefault) {
        this.generatorSupplier = generatorSupplier;
        this.rules = rules;
        this.isAutoByDefault = isAutoByDefault;
        this.depthByDefault = depthByDefault;
    }

    boolean isEmbedded(GenType type) {
        final Class<?> targetType = type.raw();
        final Generator<?> generator = generatorSupplier.get(targetType);
        return generator instanceof EmbeddedGenerator;
    }

    @NotNull
    List<GenField> scan(GenType target) {
        final Class<?> targetType = target.raw();
        final Generator<?> generator = generatorSupplier.get(targetType);
        if (!(generator instanceof EmbeddedGenerator)) {
            return Collections.emptyList();
        }

        final List<ScanField> validFields = getValidFields(targetType);
        final Set<String> ignored = rules.find(targetType)
                .map(GenRule::getExcludedFields)
                .orElse(Collections.emptySet());

        final List<ScanField> fields = validFields.stream()
                .filter(f -> !f.field().isAnnotationPresent(GenIgnore.class))
                .filter(f -> !ignored.contains(f.field().getName()))
                .collect(Collectors.toList());

        return fields.stream()
                .map(field -> convertToGenField(targetType, field).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Optional<GenField> convertToGenField(Class<?> target, ScanField scanField) {
        final boolean isComplex = isComplex(scanField.type());
        final Optional<GenRule> rule = rules.find(target);
        final Integer depth = rule.flatMap(GenRule::getDepth)
                .orElseGet(() -> {
                    final GenDepth annotation = scanField.field().getAnnotation(GenDepth.class);
                    final Integer expectedDepth = (annotation != null)
                            ? Integer.valueOf(annotation.value())
                            : Arrays.stream(target.getDeclaredAnnotations())
                                    .filter(a -> a instanceof GenDepth)
                                    .map(a -> ((GenDepth) a).value())
                                    .findFirst()
                                    .orElse(null);

                    if (expectedDepth != null && (expectedDepth < 1 || expectedDepth > GenDepth.MAX)) {
                        throw new IllegalArgumentException(
                                "Depth must be between 1 and 50, but was " + expectedDepth + " for " + target);
                    }

                    return expectedDepth;
                });

        final Optional<Generator<?>> ruleGenerator = rule
                .flatMap(r -> r.find(scanField.type().raw(), scanField.field().getName()));

        if (ruleGenerator.isPresent()) {
            return Optional.of(GenField.ofRule(scanField.field(), scanField.type(), ruleGenerator.get(), isComplex, depth));
        }

        for (Annotation marker : scanField.field().getDeclaredAnnotations()) {
            if (IS_AUTO.test(marker)) {
                final Generator<?> generator = generatorSupplier.get(scanField.type().raw(), scanField.field().getName());
                return Optional.of(GenField.ofAuto(scanField.field(), scanField.type(), generator, isComplex, depth, marker));
            }

            if (IS_GEN.test(marker)) {
                final Generator<?> generator = CastUtils.instantiate(((GenCustom) marker).value());
                return Optional.of(GenField.ofMarker(scanField.field(), scanField.type(), generator, isComplex, depth, marker));
            }

            for (Annotation custom : marker.annotationType().getDeclaredAnnotations()) {
                if (IS_FACTORY.test(custom)) {
                    final AnnotationGeneratorFactory generatorFactory = CastUtils
                            .instantiate(((GenCustomFactory) custom).value());
                    final Generator<?> generator = generatorFactory.get(marker);
                    return Optional
                            .of(GenField.ofMarker(scanField.field(), scanField.type(), generator, isComplex, depth, marker));
                }

                if (IS_GEN.test(custom)) {
                    final Generator<?> generator = CastUtils.instantiate(((GenCustom) custom).value());
                    return Optional
                            .of(GenField.ofMarker(scanField.field(), scanField.type(), generator, isComplex, depth, marker));
                }
            }
        }

        if (rule.flatMap(GenRule::isAuto).orElse(isAutoByDefault)) {
            final Generator<?> generator = generatorSupplier.get(scanField.type().raw(), scanField.field().getName());
            return Optional.of(GenField.ofAuto(scanField.field(), scanField.type(), generator, isComplex, depth, null));
        }

        return Optional.empty();
    }

    private static boolean isComplex(GenType genType) {
        final Class<?> declaringClass = genType.raw();
        return (Collection.class.isAssignableFrom(declaringClass)
                || List.class.isAssignableFrom(declaringClass)
                || Set.class.isAssignableFrom(declaringClass)
                || Map.class.isAssignableFrom(declaringClass))
                || declaringClass.getTypeName().endsWith("[][]")
                || declaringClass.getTypeName().endsWith("[]")
                || declaringClass.isEnum();
    }

    private static class ScanField {

        private final Field field;
        private final GenType type;

        private ScanField(Field field, GenType type) {
            this.field = field;
            this.type = type;
        }

        public Field field() {
            return field;
        }

        public GenType type() {
            return type;
        }
    }

    @NotNull
    private List<ScanField> getValidFields(Type target) {
        if (target == null || Object.class.equals(target)) {
            return new ArrayList<>();
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
                .flatMap(f -> {
                    if (f.getGenericType() instanceof TypeVariable && target instanceof ParameterizedType) {
                        final TypeVariable<? extends Class<?>>[] typeParameters = targetClass.getTypeParameters();
                        for (int i = 0; i < typeParameters.length; i++) {
                            if (typeParameters[i].getTypeName().equals(f.getGenericType().getTypeName())) {
                                return Stream.of(new ScanField(f,
                                        GenType.ofType(((ParameterizedType) target).getActualTypeArguments()[i])));
                            }
                        }
                    }

                    return Stream.of(new ScanField(f, GenType.ofType(f.getGenericType())));
                })
                .collect(Collectors.toList());

        superFields.addAll(targetFields);
        return superFields;
    }
}
