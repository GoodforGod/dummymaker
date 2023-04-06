package io.dummymaker;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 27.03.2023
 */
final class GenRulesContext {

    private final List<GenRuleContext> rules;

    GenRulesContext(GenRules rules) {
        this.rules = rules.rules().stream()
                .map(GenRuleContext::new)
                .collect(Collectors.toList());
    }

    Optional<GenRuleContext> findClass(@NotNull GenType target) {
        return findClass(target.raw());
    }

    Optional<GenRuleContext> findClass(@NotNull Class<?> target) {
        return Optional.ofNullable(rules.stream()
                .filter(rule -> target.equals(rule.getTarget()))
                .findFirst()
                .orElseGet(() -> rules.stream()
                        .filter(rule -> rule.getTarget().isAssignableFrom(target))
                        .findFirst()
                        .orElseGet(() -> findGlobal().orElse(null))));
    }

    Optional<GenRuleContext> findGlobal() {
        return rules.stream()
                .filter(GenRuleContext::isGlobal)
                .findFirst();
    }
}
