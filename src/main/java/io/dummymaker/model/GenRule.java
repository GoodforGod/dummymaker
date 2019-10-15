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

    /**
     * Specific class rule
     * @param target for rule
     * @return targeted class rule
     */
    public static GenRule of(Class<?> target) {
        return new GenRule(Objects.requireNonNull(target), false, 1);
    }

    /**
     * Gen auto class rule with default 1 depth
     * All fields will be auto generated for such class if not ignored otherwise
     * @see io.dummymaker.annotation.special.GenAuto
     * @param target for rule
     * @return gen auto class rule
     */
    public static GenRule auto(Class<?> target) {
        return new GenRule(Objects.requireNonNull(target), true, 1);
    }

    /**
     * Gen auto class rule with specified depth
     * All fields will be auto generated for such class if not ignored otherwise
     * @see io.dummymaker.annotation.special.GenAuto
     * @param target for rule
     * @param depth to set
     * @return gen auto class rule
     */
    public static GenRule auto(Class<?> target, int depth) {
        if (depth < 1)
            throw new IllegalArgumentException("Depth can not be negative");

        return new GenRule(Objects.requireNonNull(target), true, depth);
    }

    /**
     * Creates global rule that will affect all classes
     * @param depth to set
     * @return global rule
     */
    public static GenRule global(int depth) {
        if (depth < 1)
            throw new IllegalArgumentException("Depth can not be negative");

        return new GenRule(null, true, depth);
    }

    /**
     * Merges rules together
     * @param rule to merge with
     * @return merged rule
     */
    GenRule merge(GenRule rule) {
        if (rule == null || !this.getTarget().equals(rule.getTarget()))
            return this;

        rules.addAll(rule.rules);
        return this;
    }

    public boolean isGlobal() {
        return target == null;
    }

    public boolean isIgnored(Field field) {
        return ignored.contains(field.getName());
    }

    /**
     * Retries desired generator for field
     * @param field targeted
     * @return generator for named field or optional generator for specific type from rules
     */
    public Optional<Class<? extends IGenerator>> getDesired(Field field) {
        if (field == null || isIgnored(field))
            return Optional.empty();

        final Optional<Class<? extends IGenerator>> namedGenerator = rules.stream()
                .filter(r -> r.getNames().contains(field.getName()))
                .findAny()
                .map(GenFieldRule::getGenerator);

        return (namedGenerator.isPresent())
                ? namedGenerator
                : rules.stream()
                        .filter(r -> field.getType().equals(r.getType()) || r.getNames().contains(field.getName()))
                        .findAny()
                        .map(GenFieldRule::getGenerator);

    }

    /**
     * Setups generator for fields which names are in sequence
     * @param generator to set
     * @param fieldNames fields with names to affect
     * @return same rule
     */
    public GenRule add(Class<? extends IGenerator> generator, String... fieldNames) {
        if (fieldNames == null || fieldNames.length == 0 || generator == null)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(generator, fieldNames);
        rules.add(rule);
        return this;
    }

    /**
     * Setups generator for fields which types are in sequence
     * @param generator to set
     * @param fieldType field type to affect
     * @return same rule
     */
    public GenRule add(Class<? extends IGenerator> generator, Class<?> fieldType) {
        if (fieldType == null || generator == null)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(generator, fieldType);
        rules.add(rule);
        return this;
    }

    /**
     * Setups fields in sequence to be ignored for generating values
     * @param fieldNames fields with names to affect
     * @return same rule
     */
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
