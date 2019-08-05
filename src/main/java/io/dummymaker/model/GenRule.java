package io.dummymaker.model;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.util.StringUtils;

import java.util.*;
import java.util.function.Predicate;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 01.08.2019
 */
public class GenRule {

    private final Class<?> target;
    private final Set<GenFieldRule> rules;

    private GenRule(Class<?> target) {
        this.target = target;
        this.rules = new HashSet<>();
    }

    public static GenRule of(Class<?> target) {
        return new GenRule(Objects.requireNonNull(target));
    }

    GenRule merge(GenRule rule) {
        if (rule == null || !this.getTarget().equals(rule.getTarget()))
            return this;

        rules.addAll(rule.rules);
        return this;
    }

    public GenRule add(String fieldName, Class<? extends IGenerator> generator) {
        if (StringUtils.isBlank(fieldName) || generator == null)
            throw new NullPointerException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(fieldName, generator);
        rules.add(rule);
        return this;
    }

    public GenRule add(Class<?> fieldType, Class<? extends IGenerator> generator) {
        if (fieldType == null || generator == null)
            throw new NullPointerException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(fieldType, generator);
        rules.add(rule);
        return this;
    }

    private boolean isRuleAbsent(GenFieldRule fieldRule) {
        return rules.stream().noneMatch(getRuleFilter(fieldRule));
    }

    private Predicate<GenFieldRule> getRuleFilter(GenFieldRule fieldRule) {
        if (fieldRule.isNamed()) {
            return GenFieldRule::isNamed;
        } else if (fieldRule.isTyped()) {
            return GenFieldRule::isTyped;
        } else {
            return GenFieldRule::isGeneral;
        }
    }

    public Class<?> getTarget() {
        return target;
    }

    public List<GenFieldRule> getRules() {
        return new ArrayList<>(rules);
    }
}
