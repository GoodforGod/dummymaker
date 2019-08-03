package io.dummymaker.container;

import java.util.*;
import java.util.stream.Collectors;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 01.08.2019
 */
public class GenRules {

    private final List<GenRule> rules;

    private GenRules() {
        this.rules = new ArrayList<>();
    }

    public static GenRules of(GenRule... rules) {
        final GenRules genRules = new GenRules();
        final Map<? extends Class<?>, List<GenRule>> collected = Arrays.stream(rules)
                .collect(Collectors.groupingBy(GenRule::getTarget));

        // Merge rules if they have same target class
        final List<GenRule> mergedRules = collected.values().stream()
                .map(genRuleList -> genRuleList.stream().reduce(GenRule::merge))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        genRules.rules.addAll(mergedRules);
        return genRules;
    }

    public GenRules add(GenRule rule) {
        if (rule == null)
            throw new NullPointerException("Rule can not be null");

        final Optional<GenRule> foundRule = rules.stream()
                .filter(r -> r.getTarget().equals(rule.getTarget()))
                .findAny();

        if(foundRule.isPresent())
            foundRule.get().merge(rule);
        else
            rules.add(rule);

        return this;
    }

    public List<GenRule> getRules() {
        return new ArrayList<>(rules);
    }
}
