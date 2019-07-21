package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenEnum;
import io.dummymaker.factory.IComplexService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Generates enum values
 *
 * @author GoodforGod
 * @see GenEnum
 * @since 01.03.2019
 */
public class EnumComplexGenerator extends BasicComplexGenerator {

    @SuppressWarnings("unchecked")
    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final IComplexService storage,
                           final int depth) {
        if (field == null)
            return null;

        final Set<String> exclude = Arrays.stream(((GenEnum) annotation).exclude()).collect(Collectors.toSet());
        final Predicate<String> excludePredicate = (exclude.isEmpty())
                ? s -> true
                : s -> !exclude.contains(s);

        final Set<String> only = Arrays.stream(((GenEnum) annotation).only()).collect(Collectors.toSet());
        final Predicate<String> onlyPredicate = (exclude.isEmpty())
                ? s -> true
                : only::contains;

        final List<Field> candidates = Arrays.stream(field.getType().getFields())
                .filter(f -> onlyPredicate.test(f.getName()))
                .filter(f -> excludePredicate.test(f.getName()))
                .collect(Collectors.toList());

        final int i = ThreadLocalRandom.current().nextInt(0, candidates.size());

        return Enum.valueOf((Class<? extends Enum>) field.getType(), field.getType().getFields()[i].getName());
    }

    @Override
    public Object generate() {
        return null;
    }
}
