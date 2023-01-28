package io.dummymaker.factory;

import static io.dummymaker.util.CastUtils.castObject;

import io.dummymaker.error.GenConstructionException;
import io.dummymaker.error.GenException;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.parameterized.SequenceParameterizedGenerator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.util.CastUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
final class DefaultGenFactory implements GenFactory {

    private final ClassConstructor zeroArgConstructor;
    private final ClassConstructor fullArgConstructor;
    private final GenGraphBuilder graphBuilder;
    private final GeneratorSupplier generatorSupplier;
    private final boolean ignoreErrors;
    private final boolean overrideDefaultValues;
    private final Localisation localisation;

    DefaultGenFactory(long seed,
                      GenRules rules,
                      boolean isAutoByDefault,
                      int depthByDefault,
                      boolean ignoreErrors,
                      boolean overrideDefaultValues,
                      Localisation localisation) {
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

        final GenNode graph = graphBuilder.build(first.getClass());
        final GenContext context = GenContext.ofNew(graph.value().depth(), graph);
        return LongStream.range(0, size)
                .mapToObj(i -> fillEntity(first, context));
    }

    @Nullable
    <T> T fillEntity(@Nullable T value, GenContext context) {
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

                final Object generated = generateObject(fieldMeta, context);
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

    private Object generateObject(GenContainer fieldMeta, GenContext context) {
        final Field field = fieldMeta.field();
        final Generator<?> generator = fieldMeta.getGenerator();

        Object generated;

        if (generator instanceof EmbeddedGenerator) {
            generated = generateEmbeddedObject(fieldMeta, context);
        } else if (generator instanceof SequenceParameterizedGenerator) {
            final Object sequence = generator.get();
            generated = CastUtils.castToNumber(sequence, field.getType());
        } else if (generator instanceof ParameterizedGenerator) {
            // If complexGen can generate embedded objects
            // And not handling it like AbstractComplexGenerator, you are probably StackOverFlowed
            generated = ((ParameterizedGenerator) generator).get(localisation, fieldMeta.type(), new GenTypeBuilder() {

                @Override
                public <T> @Nullable T build(@NotNull Class<T> type) {
                    final Generator<?> suitable = generatorSupplier.get(type);
                    if (suitable instanceof EmbeddedGenerator) {
                        final GenContext embeddedContext = GenContext.ofChild(context, type);
                        if (embeddedContext.depthCurrent() <= embeddedContext.depthMax()) {
                            final T instantiate = instantiate(type);
                            return fillEntity(instantiate, embeddedContext);
                        } else {
                            return null;
                        }
                    } else {
                        return (T) suitable.get();
                    }
                }
            });
        } else if (generator instanceof LocalizedGenerator) {
            generated = ((LocalizedGenerator<?>) generator).get(localisation);
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
            return fillEntity(childValue, GenContext.ofChild(context, childType));
        } else {
            return null;
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
