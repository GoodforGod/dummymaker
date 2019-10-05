package io.dummymaker.model;

import io.dummymaker.generator.IGenerator;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Rule for settings generator type for specific field name or field type
 *
 * @author GoodforGod
 * @since 01.08.2019
 */
public class GenRule {

    private final Set<String> ignored = new HashSet<>();

    private final int depth;
    private final boolean isAuto;
    private final Class<?> target;
    private final Set<GenFieldRule> rules;

    private GenRule(Class<?> target, boolean isAuto, int depth) {
        this.isAuto = isAuto;
        this.depth = depth;
        this.target = target;
        this.rules = new HashSet<>();
    }

    public static GenRule of(Class<?> target) {
        return new GenRule(Objects.requireNonNull(target), false, 1);
    }

    public static GenRule auto(Class<?> target) {
        return new GenRule(Objects.requireNonNull(target), true, 1);
    }

    public static GenRule auto(Class<?> target, int depth) {
        if (depth < 1)
            throw new IllegalArgumentException("Depth can not be negative");

        return new GenRule(Objects.requireNonNull(target), true, depth);
    }

    GenRule merge(GenRule rule) {
        if (rule == null || !this.getTarget().equals(rule.getTarget()))
            return this;

        rules.addAll(rule.rules);
        return this;
    }

    public boolean isIgnored(Field field) {
        return ignored.contains(field.getName());
    }

    public Optional<Class<? extends IGenerator>> getDesired(Field field) {
        if (field == null || isIgnored(field))
            return Optional.empty();

        return rules.stream()
                .filter(r -> field.getType().equals(r.getType()) || r.getName().contains(field.getName()))
                .findAny()
                .map(GenFieldRule::getGenerator);
    }

    public GenRule add(Class<? extends IGenerator> generator, String... fieldNames) {
        if (fieldNames == null || fieldNames.length == 0 || generator == null)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(generator, fieldNames);
        rules.add(rule);
        return this;
    }

    public GenRule add(Class<? extends IGenerator> generator, Class<?> fieldType) {
        if (fieldType == null || generator == null)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(generator, fieldType);
        rules.add(rule);
        return this;
    }

    public GenRule ignore(String... fieldNames) {
        if (fieldNames == null || fieldNames.length == 0)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        this.ignored.addAll(Arrays.asList(fieldNames));
        return this;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public Class<?> getTarget() {
        return target;
    }

    public List<GenFieldRule> getRules() {
        return new ArrayList<>(rules);
    }
}
