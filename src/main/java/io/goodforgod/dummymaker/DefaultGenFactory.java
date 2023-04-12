package io.goodforgod.dummymaker;

import static io.goodforgod.dummymaker.util.CastUtils.castObject;

import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.error.GenException;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.simple.EmbeddedGenerator;
import io.goodforgod.dummymaker.generator.simple.number.SequenceGenerator;
import io.goodforgod.dummymaker.util.CastUtils;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    private final GenRules rules;
    private final GeneratorSupplier generatorSupplier;
    private final NamingCase namingCase;
    private final boolean ignoreErrors;
    private final boolean overrideDefaultValues;
    @Nullable
    private final Localisation localisation;
    private final boolean isAutoByDefault;
    private final int depthByDefault;

    DefaultGenFactory(long seed,
                      GenRules rules,
                      boolean isAutoByDefault,
                      int depthByDefault,
                      boolean ignoreErrors,
                      boolean overrideDefaultValues,
                      NamingCase namingCase,
                      @Nullable Localisation localisation) {
        this.rules = rules;
        this.namingCase = namingCase;
        this.ignoreErrors = ignoreErrors;
        this.overrideDefaultValues = overrideDefaultValues;
        this.localisation = localisation;
        this.generatorSupplier = new GeneratorSupplier(seed);
        this.isAutoByDefault = isAutoByDefault;
        this.depthByDefault = depthByDefault;
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

        final GenRulesContext rulesContext = rules.context();
        final Generator<?> generator = rulesContext.findClass(target)
                .flatMap(rule -> rule.find(target))
                .orElseGet(() -> generatorSupplier.get(target));

        if (!(generator instanceof EmbeddedGenerator)) {
            return Stream.generate(() -> (T) generator.get())
                    .limit(size)
                    .filter(Objects::nonNull);
        }

        final Optional<GenType> genType = DefaultGenType.ofInstantiatable(target);
        if (!genType.isPresent()) {
            throw new GenException("Type can't be instantiated: " + target);
        }

        final GenContext context = getContext(target, rulesContext);
        final Supplier<T> instanceSupplier = getInstanceSupplier(context.graph().value(), context);
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

        final Class<?> target = first.getClass();
        final GenRulesContext rulesContext = rules.context();
        final Generator<?> generator = rulesContext.findClass(target)
                .flatMap(rule -> rule.find(target))
                .orElseGet(() -> generatorSupplier.get(target));

        if (!(generator instanceof EmbeddedGenerator)) {
            return Stream.generate(supplier)
                    .limit(size)
                    .filter(Objects::nonNull);
        }

        final GenContext context = getContext(target, rulesContext);
        return Stream.generate(supplier)
                .limit(size)
                .filter(Objects::nonNull)
                .map(instance -> fillInstance(instance, context));
    }

    private GenContext getContext(Class<?> target, GenRulesContext rulesContext) {
        final GenFieldScanner fieldScanner = new GenFieldScanner(generatorSupplier, rulesContext, isAutoByDefault);
        final GenConstructorScanner constructorScanner = new GenConstructorScanner(generatorSupplier,
                rulesContext, isAutoByDefault);
        final GenGraphBuilder graphBuilder = new GenGraphBuilder(constructorScanner, fieldScanner, rulesContext,
                depthByDefault, ignoreErrors);

        final GenNode graph = graphBuilder.build(target);
        return GenContext.ofNew(graph.value().depth(), graph, rulesContext, generatorSupplier, graphBuilder);
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
        return generateFieldValue(field.type(), field.name(), field.generator(), context);
    }

    Object generateFieldValue(GenType valueType, String valueName, Generator<?> valueGenerator, GenContext context) {
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

            final GenParameterBuilder genParameterBuilder = getGenParameterBuilder(valueName, context);
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

    private GenParameterBuilder getGenParameterBuilder(String valueName, GenContext context) {
        final Class<?> parentType = context.graph().value().type().raw();
        final Localisation matchedLocalisation = (localisation == null)
                ? tryMatchLocalisation(valueName)
                : localisation;

        return new GenParameterBuilder() {

            @Override
            public <T> @Nullable T build(@NotNull Class<T> type) {
                final Generator<?> generator = context.rules().findClass(parentType)
                        .flatMap(rule -> rule.find(type))
                        .orElseGet(() -> context.generatorSupplier().get(type, valueName));

                Object generated;

                if (generator instanceof EmbeddedGenerator) {
                    final Optional<GenType> realType = DefaultGenType.ofInstantiatable(type);
                    if (!realType.isPresent()) {
                        return null;
                    }

                    if (context.depthCurrent() > context.depthMax() || isJavaInternal(realType.get())) {
                        generated = null;
                    } else {
                        final GenType genType = realType.get();
                        final GenContext checkUnknownContext = GenContext.ofChild(context, genType.raw());
                        if (checkUnknownContext.graph() == null) {
                            final GenNode graph = context.graphBuilder().build(genType.raw());
                            final GenContext childContext = GenContext.ofUnknown(graph, context);
                            generated = generateEmbeddedObject(genType, childContext);
                        } else {
                            generated = generateEmbeddedObject(genType, context);
                        }
                    }
                } else if (generator instanceof ParameterizedGenerator) {
                    final GenParameterBuilder self = this;
                    generated = ((ParameterizedGenerator<?>) generator).get(new GenParameters() {

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
                            return DefaultGenType.ofClass(type);
                        }

                        @Override
                        public @NotNull GenParameterBuilder genericBuilder() {
                            return self;
                        }
                    });
                } else {
                    generated = generator.get();
                }

                return castObject(generated, type);
            }
        };
    }

    private boolean canGenerateEmbedded(GenType type, GenContext context) {
        final Optional<GenType> realType = DefaultGenType.ofInstantiatable(type.raw());
        return context.depthCurrent() <= context.depthMax()
                && realType.isPresent();
    }

    private boolean isJavaInternal(GenType type) {
        final Class<?> typeRaw = type.raw();
        final String packageName = typeRaw.getPackage().getName();
        return packageName.equals("java")
                || packageName.equals("javax")
                || packageName.equals("jdk")
                || packageName.equals("sun");
    }

    private Object generateEmbeddedObject(GenType type, GenContext context) {
        if (!canGenerateEmbedded(type, context)) {
            return null;
        }

        final GenContext childContext = GenContext.ofChild(context, type.raw());
        final GenClass childClass = childContext.graph().value();
        final Supplier<Object> childValue = getInstanceSupplier(childClass, childContext);
        return fillInstance(childValue.get(), childContext);
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
        return () -> {
            final Object[] constructorParameters = constructor.parameters().stream()
                    .map(parameter -> generateFieldValue(parameter.type(), parameter.name(),
                            parameter.generator(), context))
                    .toArray(Object[]::new);

            try {
                return constructor.instantiate(constructorParameters);
            } catch (Exception e) {
                if (ignoreErrors) {
                    return (T) (Supplier<T>) () -> null;
                } else {
                    throw e;
                }
            }
        };
    }
}
