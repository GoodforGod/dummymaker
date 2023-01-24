package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.GenType;
import io.dummymaker.factory.GenTypeBuilder;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.util.RandomUtils;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

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
    public Object get(@NotNull Localisation localisation, @NotNull GenType fieldType, @NotNull GenTypeBuilder typeBuilder) {
        final Predicate<String> predicate;
        if (!only.isEmpty()) {
            predicate = only::contains;
        } else if (!exclude.isEmpty()) {
            predicate = s -> !exclude.contains(s);
        } else {
            predicate = s -> true;
        }

        final Field[] fields = fieldType.raw().getFields();
        final List<Field> candidates = Arrays.stream(fields)
                .filter(f -> predicate.test(f.getName()))
                .collect(Collectors.toList());

        if (candidates.isEmpty()) {
            return null;
        }

        final int index = RandomUtils.random(candidates.size());
        return Enum.valueOf((Class<? extends Enum>) fieldType.raw(), candidates.get(index).getName());
    }

    @Override
    public Object get(@NotNull Localisation localisation) {
        return null;
    }
}
