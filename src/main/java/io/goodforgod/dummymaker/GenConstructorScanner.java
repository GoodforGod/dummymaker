package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.error.GenConstructionException;
import io.goodforgod.dummymaker.error.GenException;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.CastUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.03.2023
 */
final class GenConstructorScanner {

    private final GeneratorSupplier generatorSupplier;
    private final GenRulesContext rules;
    private final boolean isAutoByDefault;

    GenConstructorScanner(GeneratorSupplier generatorSupplier, GenRulesContext rules, boolean isAutoByDefault) {
        this.generatorSupplier = generatorSupplier;
        this.rules = rules;
        this.isAutoByDefault = isAutoByDefault;
    }

    @NotNull
    GenConstructor scan(@NotNull GenType target) {
        final Constructor<?> constructor = getConstructor(target.raw());
        final List<GenParameter> parameters = getConstructorParameters(target, constructor);
        return new GenConstructor(target, constructor, parameters);
    }

    private List<GenParameter> getConstructorParameters(GenType type, Constructor<?> constructor) {
        if (constructor.getParameterCount() == 0) {
            return Collections.emptyList();
        }

        final boolean isRecord = isRecord(type.raw());
        return Arrays.stream(constructor.getParameters())
                .map(parameter -> {
                    final String parameterName;
                    if (isRecord) {
                        parameterName = parameter.getName();
                    } else if (parameter.isNamePresent()) {
                        parameterName = parameter.getName();
                    } else {
                        parameterName = Arrays.stream(type.raw().getDeclaredFields())
                                .filter(f -> Modifier.isFinal(f.getModifiers()))
                                .filter(f -> f.getType().equals(parameter.getType()))
                                .map(Field::getName)
                                .findFirst()
                                .orElseGet(() -> Arrays.stream(type.raw().getDeclaredFields())
                                        .filter(f -> f.getType().equals(parameter.getType()))
                                        .map(Field::getName)
                                        .findFirst()
                                        .orElse(parameter.getName()));
                    }

                    final GenType parameterType = GenType.ofType(parameter.getParameterizedType());
                    final Optional<GenRuleContext> typeRule = rules.findClass(type);
                    final Optional<Generator<?>> ruleGenerator = typeRule.flatMap(r -> r.find(parameterType));
                    if (ruleGenerator.isPresent()) {
                        return new GenParameter(parameterType, parameterName, ruleGenerator.get());
                    } else if (typeRule.flatMap(GenRuleContext::isAuto).orElse(isAutoByDefault)
                            || CastUtils.isPrimitive(type.raw())) {
                        final Generator<?> generator = generatorSupplier.get(parameterType.raw(), parameterName);
                        return new GenParameter(parameterType, parameterName, generator);
                    } else {
                        return new GenParameter(parameterType, parameterName, generatorSupplier.get(Void.class));
                    }
                })
                .collect(Collectors.toList());
    }

    private static Constructor<?> getConstructor(Class<?> target) {
        try {
            if (isRecord(target)) {
                final Class<?>[] constructorTypes = Arrays.stream(target.getDeclaredFields())
                        .map(Field::getType)
                        .toArray(Class<?>[]::new);

                return target.getDeclaredConstructor(constructorTypes);
            }

            // search for zero arg constructor
            final Optional<Constructor<?>> zeroArgConstructor = Arrays.stream(target.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 0)
                    .findFirst();
            if (zeroArgConstructor.isPresent()) {
                return zeroArgConstructor.get();
            }

            // search for potential inner class constructor
            final Optional<Constructor<?>> innerClassArgConstructor = Arrays.stream(target.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 1)
                    .filter(c -> CastUtils.CastType.of(c.getParameterTypes()[0]).equals(CastUtils.CastType.UNKNOWN))
                    .findFirst();
            if (innerClassArgConstructor.isPresent()) {
                return innerClassArgConstructor.get();
            }

            // search for full arg constructor
            final Optional<Constructor<?>> fullArgConstructor = Arrays.stream(target.getDeclaredConstructors())
                    .max(Comparator.comparingInt(Constructor::getParameterCount));
            if (fullArgConstructor.isPresent()) {
                return fullArgConstructor.get();
            }

            throw new GenConstructionException("Can't instantiate '" + target + "', suitable constructor not found");
        } catch (GenException e) {
            throw e;
        } catch (Exception e) {
            throw new GenConstructionException("Exception occurred during '" + target + "' class constructor search due to: ", e);
        }
    }

    private static boolean isRecord(Class<?> target) {
        return target.getSuperclass() != null && target.getSuperclass().getCanonicalName().equals("java.lang.Record");
    }
}
