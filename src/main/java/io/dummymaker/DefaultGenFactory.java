package io.dummymaker;

import io.dummymaker.cases.NamingCase;
import io.dummymaker.error.GenConstructionException;
import io.dummymaker.error.GenException;
import io.dummymaker.generator.GenParameters;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.number.SequenceGenerator;
import io.dummymaker.util.CastUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dummymaker.util.CastUtils.castObject;

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
        final GenScanner scanner = new GenScanner(generatorSupplier, rules, isAutoByDefault, depthByDefault);
        this.graphBuilder = new GenGraphBuilder(scanner, rules, depthByDefault);
    }

    @Override
    public <T> T build(@Nullable Class<T> target) {
        return stream(target, 1).findFirst().orElse(null);
    }

    @Override
    public <T> T build(@NotNull Supplier<T> supplier) {
        return stream(supplier, 1).findFirst().orElse(null);
    }

    @Override
    public @NotNull <T> List<T> build(@Nullable Class<T> target, int size) {
        return stream(target, size).collect(Collectors.toList());
    }

    @Override
    public @NotNull <T> List<T> build(@NotNull Supplier<T> supplier, int size) {
        return stream(supplier, size).collect(Collectors.toList());
    }

    @Override
    public @NotNull <T> Stream<T> stream(@Nullable Class<T> target, long size) {
        return stream(() -> instantiate(target), size);
    }

    @Override
    public @NotNull <T> Stream<T> stream(@NotNull Supplier<T> supplier, long size) {
        if (size < 1) {
            return Stream.empty();
        }

        final T first = supplier.get();
        if (first == null) {
            return Stream.empty();
        }

        final Generator<?> generator = generatorSupplier.get(first.getClass());
        if (!(generator instanceof EmbeddedGenerator)) {
            return Stream.generate(() -> (T) generator.get())
                    .limit(size)
                    .filter(Objects::nonNull);
        }

        final GenNode graph = graphBuilder.build(first.getClass());
        final GenContext context = GenContext.ofNew(graph.value().depth(), graph);
        return Stream.generate(supplier)
                .limit(size)
                .filter(Objects::nonNull)
                .map(value -> fillValueFields(value, context));
    }

    @Nullable
    <T> T fillValueFields(@Nullable T value, GenContext context) {
        if (value == null || context.graph() == null) {
            return null;
        }

        try {
            final List<GenContainer> fields = context.graph().value().fields();
            for (GenContainer fieldMeta : fields) {
                final Field field = fieldMeta.field();
                field.setAccessible(true);
                if (!overrideDefaultValues) {
                    final Object fieldDefaultValue = field.get(value);
                    if (fieldDefaultValue != null) {
                        continue;
                    }
                }

                final Object generated = generateFieldValue(fieldMeta, context);
                if (generated != null) {
                    field.set(value, generated);
                }
            }
        } catch (GenException e) {
            if (!ignoreErrors) {
                throw e;
            }
        } catch (Exception e) {
            if (!ignoreErrors) {
                throw new GenException(e);
            }
        }

        return value;
    }

    private Object generateFieldValue(GenContainer fieldMeta, GenContext context) {
        final Field field = fieldMeta.field();
        final Generator<?> generator = fieldMeta.getGenerator();
        final Localisation matchedLocalisation = (localisation == null)
                ? tryMatchLocalisation(field)
                : localisation;

        Object generated;

        if (generator instanceof EmbeddedGenerator) {
            generated = generateEmbeddedObject(fieldMeta, context);
        } else if (generator instanceof SequenceGenerator) {
            final Object sequence = generator.get();
            generated = CastUtils.castToNumber(sequence, field.getType());
        } else if (generator instanceof ParameterizedGenerator) {
            // may stackOverFlow if ton of embedded will be generated
            final GenTypeBuilder genTypeBuilder = new GenTypeBuilder() {

                @Override
                public <T> @Nullable T build(@NotNull Class<T> type) {
                    final Generator<?> suitableGenerator = generatorSupplier.get(type);
                    if (suitableGenerator instanceof EmbeddedGenerator) {
                        final GenContext embeddedContext = GenContext.ofChild(context, type);
                        if (embeddedContext.depthCurrent() <= embeddedContext.depthMax()) {
                            final T instantiate = instantiate(type);
                            return fillValueFields(instantiate, embeddedContext);
                        } else {
                            return null;
                        }
                    } else if (suitableGenerator instanceof ParameterizedGenerator) {
                        final GenTypeBuilder thisGenTypeBuilder = this;
                        return (T) ((ParameterizedGenerator<?>) suitableGenerator).get(new GenParameters() {

                            @Override
                            public @NotNull Localisation localisation() {
                                return matchedLocalisation;
                            }

                            @Override
                            public @NotNull NamingCase namingCase() {
                                return namingCase;
                            }

                            @Override
                            public @NotNull GenType fieldType() {
                                return SimpleGenType.ofClass(type);
                            }

                            @Override
                            public @NotNull GenTypeBuilder fieldTypeBuilder() {
                                return thisGenTypeBuilder;
                            }
                        });
                    } else {
                        return (T) suitableGenerator.get();
                    }
                }
            };

            generated = ((ParameterizedGenerator) generator).get(new GenParameters() {

                @Override
                public @NotNull Localisation localisation() {
                    return matchedLocalisation;
                }

                @Override
                public @NotNull NamingCase namingCase() {
                    return namingCase;
                }

                @Override
                public @NotNull GenType fieldType() {
                    return fieldMeta.type();
                }

                @Override
                public @NotNull GenTypeBuilder fieldTypeBuilder() {
                    return genTypeBuilder;
                }
            });
        } else {
            generated = generator.get();
        }

        return castObject(generated, field.getType());
    }

    private Object generateEmbeddedObject(GenContainer fieldMeta, GenContext context) {
        if (context.depthCurrent() <= context.depthMax()) {
            final Class<?> childType = fieldMeta.field().getType();
            if (childType.getPackage().getName().startsWith("java.")) {
                return null;
            }

            final Object childValue = instantiate(childType);
            return fillValueFields(childValue, GenContext.ofChild(context, childType));
        } else {
            return null;
        }
    }

    @NotNull
    private Localisation tryMatchLocalisation(Field field) {
        if (PATTERN_LOCALISATION_EN.matcher(field.getName()).matches()) {
            return Localisation.ENGLISH;
        } else if (PATTERN_LOCALISATION_RU.matcher(field.getName()).matches()) {
            return Localisation.RUSSIAN;
        } else {
            return Localisation.ENGLISH;
        }
    }

    private <T> T instantiate(Class<T> target) {
        try {
            return zeroArgConstructor.instantiate(target);
        } catch (GenConstructionException e) {
            return fullArgConstructor.instantiate(target);
        }
    }
}
