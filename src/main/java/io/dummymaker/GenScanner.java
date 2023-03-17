package io.dummymaker;

import io.dummymaker.annotation.*;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
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
final class GenScanner {

    private static class GenField {

        private final Field field;
        private final GenType type;

        private GenField(Field field, GenType type) {
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
        final Class<?> targetType = target.raw();
        final List<GenField> validFields = getValidFields(targetType);
        final Set<String> ignored = rules.find(targetType)
                .map(GenRule::getIgnored)
                .orElse(Collections.emptySet());

        final List<GenField> fields = validFields.stream()
                .filter(f -> !f.field().isAnnotationPresent(GenIgnore.class))
                .filter(f -> !ignored.contains(f.field().getName()))
                .collect(Collectors.toList());

        return fields.stream()
                .map(field -> getContainer(targetType, field).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Optional<GenContainer> getContainer(Class<?> target, GenField genField) {
        final Optional<GenRule> rule = rules.find(target);
        final int depth = rule.flatMap(GenRule::getDepth)
                .orElseGet(() -> {
                    final GenDepth annotation = genField.field().getAnnotation(GenDepth.class);
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

        final Optional<Generator<?>> ruleGenerator = rule.flatMap(r -> r.find(genField.type().raw(), genField.field().getName()));
        final boolean isComplex = isComplex(genField.type());
        if (ruleGenerator.isPresent()) {
            return Optional.of(GenContainer.ofRule(genField.field(), genField.type(), ruleGenerator.get(), depth, isComplex));
        }

        for (Annotation marker : genField.field().getDeclaredAnnotations()) {
            if (IS_AUTO.test(marker)) {
                final Generator<?> generator = generatorSupplier.get(genField.type().raw(), genField.field().getName());
                return Optional.of(GenContainer.ofAuto(genField.field(), genField.type(), generator, depth, isComplex, marker));
            }

            if (IS_GEN.test(marker)) {
                final Generator<?> generator = CastUtils.instantiate(((GenCustom) marker).value());
                return Optional.of(GenContainer.ofMarker(genField.field(), genField.type(), generator, depth, isComplex, marker));
            }

            for (Annotation custom : marker.annotationType().getDeclaredAnnotations()) {
                if (IS_FACTORY.test(custom)) {
                    final AnnotationGeneratorFactory generatorFactory = CastUtils
                            .instantiate(((GenCustomFactory) custom).value());
                    final Generator<?> generator = generatorFactory.get(marker);
                    return Optional
                            .of(GenContainer.ofMarker(genField.field(), genField.type(), generator, depth, isComplex, marker));
                }

                if (IS_GEN.test(custom)) {
                    final Generator<?> generator = CastUtils.instantiate(((GenCustom) custom).value());
                    return Optional
                            .of(GenContainer.ofMarker(genField.field(), genField.type(), generator, depth, isComplex, marker));
                }
            }
        }

        if (rule.flatMap(GenRule::isAuto).orElse(isAutoByDefault)) {
            final Generator<?> generator = generatorSupplier.get(genField.type().raw(), genField.field().getName());
            return Optional.of(GenContainer.ofAuto(genField.field(), genField.type(), generator, depth, isComplex, null));
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

    @NotNull
    private List<GenField> getValidFields(Type target) {
        if (target == null || Object.class.equals(target)) {
            return new ArrayList<>();
        }

        final Class<?> targetClass = (target instanceof ParameterizedType)
                ? (Class<?>) ((ParameterizedType) target).getRawType()
                : ((Class<?>) target);

        final List<GenField> superFields = getValidFields(targetClass.getGenericSuperclass());
        final List<GenField> targetFields = Arrays.stream(targetClass.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isNative(f.getModifiers()))
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .filter(f -> !Modifier.isSynchronized(f.getModifiers()))
                .flatMap(f -> {
                    if (f.getGenericType() instanceof TypeVariable && target instanceof ParameterizedType) {
                        final TypeVariable<? extends Class<?>>[] typeParameters = targetClass.getTypeParameters();
                        for (int i = 0; i < typeParameters.length; i++) {
                            if (typeParameters[i].getTypeName().equals(f.getGenericType().getTypeName())) {
                                return Stream.of(new GenField(f,
                                        SimpleGenType.ofType(((ParameterizedType) target).getActualTypeArguments()[i])));
                            }
                        }
                    }

                    return Stream.of(new GenField(f, SimpleGenType.ofType(f.getGenericType())));
                })
                .collect(Collectors.toList());

        superFields.addAll(targetFields);
        return superFields;
    }
}
