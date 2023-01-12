package io.dummymaker.generator.complex;

import io.dummymaker.annotation.complex.GenEnum;
import io.dummymaker.factory.old.GenStorage;
import io.dummymaker.util.RandomUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Generates enum values
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenEnum
 * @since 01.03.2019
 */
public class EnumComplexGenerator extends AbstractComplexGenerator {

    @SuppressWarnings("unchecked")
    @Override
    public Object generate(final @NotNull Class<?> parent,
                           final @NotNull Field field,
                           final @NotNull GenStorage storage,
                           final Annotation annotation,
                           final int depth) {
        final Set<String> exclude = getExcluded(annotation);
        final Predicate<String> excludePredicate = (exclude.isEmpty())
                ? s -> true
                : s -> !exclude.contains(s);

        final Set<String> only = getOnly(annotation);
        final Predicate<String> onlyPredicate = (exclude.isEmpty())
                ? s -> true
                : only::contains;

        final List<Field> candidates = Arrays.stream(field.getType().getFields())
                .filter(f -> onlyPredicate.test(f.getName()))
                .filter(f -> excludePredicate.test(f.getName()))
                .collect(Collectors.toList());

        final int i = RandomUtils.random(candidates.size());
        return Enum.valueOf((Class<? extends Enum>) field.getType(), field.getType().getFields()[i].getName());
    }

    @Override
    public Object get() {
        return null;
    }

    private Set<String> getExcluded(Annotation annotation) {
        if (annotation == null || !annotation.annotationType().equals(GenEnum.class))
            return Collections.emptySet();

        return Arrays.stream(((GenEnum) annotation).exclude()).collect(Collectors.toSet());
    }

    private Set<String> getOnly(Annotation annotation) {
        if (annotation == null || !annotation.annotationType().equals(GenEnum.class))
            return Collections.emptySet();

        return Arrays.stream(((GenEnum) annotation).only()).collect(Collectors.toSet());
    }
}
