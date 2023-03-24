package io.dummymaker;

import static io.dummymaker.util.CastUtils.castObject;

import io.dummymaker.cases.NamingCase;
import io.dummymaker.error.GenException;
import io.dummymaker.generator.GenParameters;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.number.SequenceGenerator;
import io.dummymaker.util.CastUtils;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
final class DefaultGenFactory implements GenFactory {

    private static final Pattern PATTERN_LOCALISATION_EN = Pattern.compile("(Eng?|English)$");
    private static final Pattern PATTERN_LOCALISATION_RU = Pattern.compile("(Ru|Russian)$");

    private final ClassConstructor zeroArgConstructor;
    private final ClassConstructor fullArgConstructor;
    private final GenGraphBuilder graphBuilder;
    private final GeneratorSupplier generatorSupplier;
    private final NamingCase namingCase;
    private final boolean ignoreErrors;
    private final boolean overrideDefaultValues;
    @Nullable
    private final Localisation localisation;

    DefaultGenFactory(long seed,
                      GenRules rules,
                      boolean isAutoByDefault,
                      int depthByDefault,
                      boolean ignoreErrors,
                      boolean overrideDefaultValues,
                      NamingCase namingCase,
                      @Nullable Localisation localisation) {
        this.namingCase = namingCase;
        this.ignoreErrors = ignoreErrors;
        this.overrideDefaultValues = overrideDefaultValues;
        this.localisation = localisation;
        this.generatorSupplier = new RuleBasedGeneratorSupplier(rules, new DefaultGeneratorSupplier(seed));
        this.zeroArgConstructor = new ZeroArgClassConstructor();
        this.fullArgConstructor = new FullArgClassConstructor(generatorSupplier);
        final GenFieldScanner scanner = new GenFieldScanner(generatorSupplier, rules, isAutoByDefault, depthByDefault);
        this.graphBuilder = new GenGraphBuilder(scanner, rules, depthByDefault);
    }

    @Override
    public <T> T build(@NotNull Class<T> target) {
        return stream(target, 1).findFirst().orElse(null);
    }

    @Override
    public <T> T build(@NotNull Supplier<T> supplier) {
        return stream(supplier, 1).findFirst().orElse(null);
    }

    @Override
    public @NotNull <T> List<T> build(@NotNull Class<T> target, int size) {
        return stream(target, size).collect(Collectors.toList());
    }

    @Override
    public @NotNull <T> List<T> build(@NotNull Supplier<T> supplier, int size) {
        return stream(supplier, size).collect(Collectors.toList());
    }

    @Override
    public @NotNull <T> Stream<T> stream(@NotNull Class<T> target, long size) {
        if (size < 0) {
            throw new GenException("Generation size can't be less than 0");
        } else if (size == 0) {
            return Stream.empty();
        }

        final Generator<?> generator = generatorSupplier.get(target);
        if (!(generator instanceof EmbeddedGenerator)) {
            return Stream.generate(() -> (T) generator.get())
                    .limit(size)
                    .filter(Objects::nonNull);
        }

        final GenNode graph = graphBuilder.build(target);
        final GenContext context = GenContext.ofNew(graph.value().depth(), graph);
        final Supplier<T> instanceSupplier = getInstanceSupplier(graph.value(), context);
        return Stream.generate(instanceSupplier)
                .limit(size)
                .map(instance -> fillInstance(instance, context));
    }

    @Override
    public @NotNull <T> Stream<T> stream(@NotNull Supplier<T> supplier, long size) {
        if (size < 0) {
            throw new GenException("Generation size can't be less than 0");
        } else if (size == 0) {
            return Stream.empty();
        }

        final T first = supplier.get();
        if (first == null) {
            return Stream.empty();
        }

        final Generator<?> generator = generatorSupplier.get(first.getClass());
        if (!(generator instanceof EmbeddedGenerator)) {
            return Stream.generate(supplier)
                    .limit(size)
                    .filter(Objects::nonNull);
        }

        final GenNode graph = graphBuilder.build(first.getClass());
        final GenContext context = GenContext.ofNew(graph.value().depth(), graph);
        return Stream.generate(supplier)
                .limit(size)
                .filter(Objects::nonNull)
                .map(instance -> fillInstance(instance, context));
    }

    @Nullable
    <T> T fillInstance(@Nullable T instance, GenContext context) {
        if (instance == null || context.graph() == null) {
            return null;
        }

        try {
            final List<GenField> fields = context.graph().value().fields();
            for (GenField field : fields) {
                if (!overrideDefaultValues) {
                    final Object fieldDefaultValue = field.getDefaultValue(instance);
                    if (fieldDefaultValue != null) {
                        continue;
                    }
                }

                final Object generated = generateFieldValue(field, context);
                if (generated != null) {
                    field.setValue(instance, generated);
                }
            }
        } catch (GenException e) {
            if (!ignoreErrors) {
                throw e;
            }
        } catch (Exception e) {
            if (!ignoreErrors) {
                throw new GenException("Error occurred while generating '" + instance.getClass() + "' field value due to: ", e);
            }
        }

        return instance;
    }

    Object generateFieldValue(GenField field, GenContext context) {
        return generateFieldValue(field.generator(), field.type(), field.name(), context);
    }

    Object generateFieldValue(Generator<?> valueGenerator, GenType valueType, String valueName, GenContext context) {
        Object generated;

        if (valueGenerator instanceof EmbeddedGenerator) {
            generated = generateEmbeddedObject(valueType, context);
        } else if (valueGenerator instanceof SequenceGenerator) {
            final Object sequence = valueGenerator.get();
            generated = CastUtils.castToNumber(sequence, valueType.raw());
        } else if (valueGenerator instanceof ParameterizedGenerator) {
            final Localisation matchedLocalisation = (localisation == null)
                    ? tryMatchLocalisation(valueName)
                    : localisation;

            final GenParameterBuilder genParameterBuilder = getGenParameterBuilder(matchedLocalisation, context);
            generated = ((ParameterizedGenerator) valueGenerator).get(new GenParameters() {

                @Override
                public @NotNull Localisation localisation() {
                    return matchedLocalisation;
                }

                @Override
                public @NotNull NamingCase namingCase() {
                    return namingCase;
                }

                @Override
                public @NotNull GenType parameterType() {
                    return valueType;
                }

                @Override
                public @NotNull GenParameterBuilder genericBuilder() {
                    return genParameterBuilder;
                }
            });
        } else {
            generated = valueGenerator.get();
        }

        return castObject(generated, valueType.raw());
    }

    private GenParameterBuilder getGenParameterBuilder(@NotNull Localisation localisation, @NotNull GenContext context) {
        return new GenParameterBuilder() {

            @Override
            public <T> @Nullable T build(@NotNull Class<?> type) {
                final Generator<?> suitableGenerator = generatorSupplier.get(type);
                if (suitableGenerator instanceof EmbeddedGenerator) {
                    return (T) generateEmbeddedObject(GenType.ofClass(type), context);
                } else if (suitableGenerator instanceof ParameterizedGenerator) {
                    final GenParameterBuilder thisGenParameterBuilder = this;
                    return (T) ((ParameterizedGenerator<?>) suitableGenerator).get(new GenParameters() {

                        @Override
                        public @NotNull Localisation localisation() {
                            return localisation;
                        }

                        @Override
                        public @NotNull NamingCase namingCase() {
                            return namingCase;
                        }

                        @Override
                        public @NotNull GenType parameterType() {
                            return GenType.ofClass(type);
                        }

                        @Override
                        public @NotNull GenParameterBuilder genericBuilder() {
                            return thisGenParameterBuilder;
                        }
                    });
                } else {
                    return (T) suitableGenerator.get();
                }
            }
        };
    }

    private Object generateEmbeddedObject(GenType type, GenContext context) {
        if (context.depthCurrent() <= context.depthMax()) {
            final Class<?> childType = type.raw();
            if (childType.getPackage().getName().startsWith("java.")) {
                return null;
            }

            final GenContext childContext = GenContext.ofChild(context, childType);
            final GenClass childClass;
            try {
                childClass = childContext.graph().value();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            final Supplier<Object> childValue = getInstanceSupplier(childClass, childContext);
            return fillInstance(childValue.get(), childContext);
        } else {
            return null;
        }
    }

    @NotNull
    private Localisation tryMatchLocalisation(String fieldName) {
        if (PATTERN_LOCALISATION_EN.matcher(fieldName).matches()) {
            return Localisation.ENGLISH;
        } else if (PATTERN_LOCALISATION_RU.matcher(fieldName).matches()) {
            return Localisation.RUSSIAN;
        }

        return Localisation.ENGLISH;
    }

    private <T> Supplier<T> getInstanceSupplier(GenClass genClass, GenContext context) {
        final GenConstructor constructor = genClass.constructor();
        final Object[] constructorParameters = constructor.parameters().stream()
                .map(parameter -> {
                    final Generator<?> generator = generatorSupplier.get(parameter.type().raw(), parameter.name());
                    return generateFieldValue(generator, parameter.type(), parameter.name(), context);
                })
                .toArray(Object[]::new);

        return () -> constructor.instantiate(constructorParameters);
    }
}
