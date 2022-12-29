package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 14.12.2022
 */
public final class EnumParameterizedGenerator implements ParameterizedGenerator<Object> {

    private final Set<String> only;
    private final Set<String> exclude;

    public EnumParameterizedGenerator(String[] only, String[] exclude) {
        this.only = new HashSet<>(Arrays.asList(only));
        this.exclude = new HashSet<>(Arrays.asList(exclude));
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        final Predicate<String> excludePredicate = (exclude.isEmpty())
                ? s -> true
                : s -> !exclude.contains(s);

        final Predicate<String> onlyPredicate = (exclude.isEmpty())
                ? s -> true
                : only::contains;

        final Field[] fields = fieldType.value().getFields();
        final List<Field> candidates = Arrays.stream(fields)
                .filter(f -> onlyPredicate.test(f.getName()))
                .filter(f -> excludePredicate.test(f.getName()))
                .collect(Collectors.toList());

        final int index = RandomUtils.random(candidates.size());
        return Enum.valueOf((Class<? extends Enum>) fieldType.value(), candidates.get(index).getName());
    }

    @Override
    public Object get() {
        return null;
    }
}
