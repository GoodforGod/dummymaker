package io.dummymaker.factory.refactored;

import io.dummymaker.factory.old.GenFactory;
import io.dummymaker.factory.old.GenSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Rules for field type and field name generators overrides Allows to override gen auto generator
 * setup without annotations Just by passing this config to factory
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenFactory
 * @see GenSupplier
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

    public @NotNull Optional<GenRule> find(@Nullable Class<?> target) {
        if (target == null)
            return Optional.empty();

        return Optional.ofNullable(rules.stream()
                .filter(r -> r.getTarget().equals(target))
                .findFirst()
                .orElseGet(() -> rules.stream()
                        .filter(GenRule::isGlobal)
                        .findFirst()
                        .orElse(null)));
    }

    public List<GenRule> getRules() {
        return rules;
    }
}
