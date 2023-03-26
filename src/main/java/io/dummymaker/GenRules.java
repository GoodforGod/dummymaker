package io.dummymaker;

import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

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

    static @NotNull GenRules of(@NotNull List<GenRule> rules) {
        final Map<Class<?>, List<GenRule>> collected = rules.stream()
                .collect(Collectors.groupingBy(GenRule::getTarget));

        final Optional<GenRule> global = rules.stream()
                .filter(GenRule::isGlobal)
                .findFirst();

        // Merge rules if they have same target class
        final List<GenRule> genRules = collected.values().stream()
                .map(r -> r.stream().reduce(GenRule::merge)
                        .map(rule -> global.map(rule::merge).orElse(rule))
                        .orElse(null))
                .filter(Objects::nonNull)
                .map(GenRule::build)
                .collect(Collectors.toList());

        return new GenRules(genRules);
    }

    List<GenRule> rules() {
        return rules;
    }

    GenRulesContext context() {
        return new GenRulesContext(this);
    }
}
