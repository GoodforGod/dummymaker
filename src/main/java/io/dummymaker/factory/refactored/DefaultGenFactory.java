package io.dummymaker.factory.refactored;

import io.dummymaker.error.ClassConstructorException;
import io.dummymaker.error.GenException;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.parameterized.SequenceParameterizedGenerator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.util.CastUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static io.dummymaker.util.CastUtils.castObject;

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

    DefaultGenFactory(long salt, GenRules rules, boolean isAutoByDefault, int depthByDefault, boolean ignoreErrors) {
        this.generatorSupplier = new DefaultGeneratorSupplier(salt);
        this.zeroArgConstructor = new ZeroArgClassConstructor();
        this.fullArgConstructor = new FullArgClassConstructor(rules, generatorSupplier);
        final GenScanner scanner = new GenScanner(generatorSupplier, rules, isAutoByDefault, depthByDefault);
        this.graphBuilder = new GenGraphBuilder(scanner, rules, depthByDefault);
        this.ignoreErrors = ignoreErrors;
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
        final GenContext context = SimpleGenContext.ofNew(graph.value().depth(), graph);
        return LongStream.range(0, size)
                .mapToObj(i -> fillEntity(first, context));
    }

    @Nullable <T> T fillEntity(@Nullable T value, GenContext context) {
        if (value == null || context.graph() == null) {
            return value;
        }

        try {
            final List<GenContainer> fields = context.graph().value().fields();
            for (GenContainer fieldMeta : fields) {
                final Field field = fieldMeta.field();
                field.setAccessible(true);
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
            generated = CastUtils.castToNumber(generator.get(), field.getType());
        } else if (generator instanceof ParameterizedGenerator) {
            // If complexGen can generate embedded objects
            // And not handling it like AbstractComplexGenerator, you are probably StackOverFlowed
            final GenContext innerContext = SimpleGenContext.ofParameterized(context, fieldMeta.type());
            generated = ((ParameterizedGenerator) generator).get(fieldMeta.type(), new TypeBuilder() {

                @Override
                public <T> @Nullable T build(@NotNull Class<T> type) {
                    final Generator<?> suitable = generatorSupplier.get(type);
                    if (suitable instanceof EmbeddedGenerator) {
                        final GenContext embeddedContext = SimpleGenContext.ofChild(innerContext, type);
                        if (embeddedContext.depthCurrent() <= embeddedContext.depthMax()) {
                            final T instantiate = instantiate(type);
                            T filled = fillEntity(instantiate, embeddedContext);
                            return filled;
                        } else {
                            return null;
                        }
                    } else {
                        return (T) suitable.get();
                    }
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
            return fillEntity(childValue, SimpleGenContext.ofChild(context, childType));
        } else {
            return null;
        }
    }

    private <T> T instantiate(Class<T> target) {
        try {
            return zeroArgConstructor.instantiate(target);
        } catch (ClassConstructorException e) {
            return fullArgConstructor.instantiate(target);
        }
    }
}
