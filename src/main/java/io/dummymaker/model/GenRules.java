package io.dummymaker.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Rules for field type and field name generators overrides Allows to override
 * gen auto generator setup without annotations Just by passing this config to
 * factory
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.IGenFactory
 * @see io.dummymaker.factory.IGenSupplier
 * @since 01.08.2019
 */
public class GenRules {

    private final List<GenRule> rules;

    private GenRules() {
        this.rules = new ArrayList<>();
    }

    /**
     * Initialize gen rules with rules in sequence
     *
     * @param rules to add
     * @return gen rules
     */
    public static GenRules of(GenRule... rules) {
        final GenRules genRules = new GenRules();
        final Map<? extends Class<?>, List<GenRule>> collected = Arrays.stream(rules)
                .collect(Collectors.groupingBy(GenRule::getTarget));

        // Merge rules if they have same target class
        collected.values().stream()
                .map(genRuleList -> genRuleList.stream().reduce(GenRule::merge))
                .forEach(r -> r.ifPresent(genRules.rules::add));

        return genRules;
    }

    /**
     * Retrieves targeted rule for such class
     *
     * @param target to check
     * @return rule
     */
    public Optional<GenRule> targeted(Class<?> target) {
        if (target == null)
            return Optional.empty();

        final Optional<GenRule> targetRule = rules.stream()
                .filter(r -> r.getTarget().equals(target))
                .findFirst();

        return targetRule.isPresent()
                ? targetRule
                : rules.stream()
                        .filter(GenRule::isGlobal)
                        .findFirst();
    }

    /**
     * Add rule to list
     *
     * @param rule to add
     * @return same gen rules
     */
    public GenRules add(GenRule rule) {
        final Optional<GenRule> foundRule = rules.stream()
                .filter(r -> r.getTarget().equals(rule.getTarget()))
                .findAny();

        if (foundRule.isPresent())
            foundRule.get().merge(rule);
        else
            rules.add(rule);

        return this;
    }

    public List<GenRule> getRules() {
        return new ArrayList<>(rules);
    }
}
