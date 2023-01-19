package io.dummymaker.factory;

import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Rules for field type and field name generators overrides Allows to override gen auto generator
 * setup without annotations Just by passing this config to factory
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenFactory
 * @since 01.08.2019
 */
final class GenRules {

    static final GenRules EMPTY = new GenRules(Collections.emptyList());

    private final List<GenRule> rules;

    private GenRules(List<GenRule> rules) {
        this.rules = rules;
    }

    public static @NotNull GenRules of(@NotNull List<GenRule> rules) {
        final Map<Class<?>, List<GenRule>> collected = rules.stream()
                .collect(Collectors.groupingBy(GenRule::getTarget));

        // Merge rules if they have same target class
        final List<GenRule> genRules = collected.values().stream()
                .map(r -> r.stream().reduce(GenRule::merge).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new GenRules(genRules);
    }

    @NotNull
    Optional<GenRule> find(@Nullable Class<?> target) {
        if (target == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(rules.stream()
                .filter(r -> target.isAssignableFrom(r.getTarget()))
                .findFirst()
                .orElseGet(() -> rules.stream()
                        .filter(GenRule::isGlobal)
                        .findFirst()
                        .orElse(null)));
    }

    List<GenRule> rules() {
        return rules;
    }
}
