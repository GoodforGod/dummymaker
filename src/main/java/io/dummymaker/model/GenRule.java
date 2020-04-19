package io.dummymaker.model;

import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Rule for settings generator type for specific field name or field type
 *
 * @author GoodforGod
 * @see GenRules
 * @since 01.08.2019
 */
public class GenRule {

    private static final Class<?> GLOBAL_MARKER = Object.class;

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
     *
     * @param target for rule
     * @return targeted class rule
     */
    public static @NotNull GenRule of(Class<?> target) {
        if (target == null || GLOBAL_MARKER.equals(target))
            throw new IllegalArgumentException("Invalid target class");

        return new GenRule(target, false, 1);
    }

    /**
     * Gen auto class rule with default 1 depth All fields will be auto generated
     * for such class if not ignored otherwise
     *
     * @param target for rule
     * @return gen auto class rule
     * @see io.dummymaker.annotation.special.GenAuto
     */
    public static @NotNull GenRule auto(Class<?> target) {
        if (target == null || GLOBAL_MARKER.equals(target))
            throw new IllegalArgumentException("Invalid target class");

        return new GenRule(target, true, 1);
    }

    /**
     * Gen auto class rule with specified depth All fields will be auto generated
     * for such class if not ignored otherwise
     *
     * @param target for rule
     * @param depth  to set
     * @return gen auto class rule
     * @see io.dummymaker.annotation.special.GenAuto
     */
    public static @NotNull GenRule auto(Class<?> target, int depth) {
        if (target == null || GLOBAL_MARKER.equals(target))
            throw new IllegalArgumentException("Invalid target class");

        if (depth < 1)
            throw new IllegalArgumentException("Depth can not be negative");

        return new GenRule(target, true, depth);
    }

    /**
     * Creates global rule that will affect all classes
     *
     * @param depth to set
     * @return global rule
     */
    public static @NotNull GenRule global(int depth) {
        if (depth < 1)
            throw new IllegalArgumentException("Depth can not be negative");

        return new GenRule(Object.class, true, depth);
    }

    /**
     * Merges rules together
     *
     * @param rule to merge with
     * @return merged rule
     */
    GenRule merge(GenRule rule) {
        if (rule == null || !this.getTarget().equals(rule.getTarget()))
            return this;

        final Set<GenFieldRule> equalFields = rule.rules.stream()
                .filter(r -> rules.stream().anyMatch(mr -> mr.equals(r)))
                .collect(Collectors.toSet());

        equalFields.stream()
                .filter(GenFieldRule::isTyped)
                .findFirst()
                .ifPresent(r -> {
                    throw new IllegalArgumentException("Equal typed field is present for type " + r.getType());
                });

        this.rules.addAll(rule.rules);
        return this;
    }

    public boolean isGlobal() {
        return GLOBAL_MARKER.equals(target);
    }

    public boolean isIgnored(Field field) {
        return ignored.contains(field.getName());
    }

    /**
     * Retries desired generator for field
     *
     * @param field targeted
     * @return generator for named field or optional generator for specific type
     *         from rules
     */
    public @NotNull Optional<IGenerator<?>> getDesiredExample(Field field) {
        if (field == null || isIgnored(field))
            return Optional.empty();

        final Optional<IGenerator<?>> namedGenerator = this.rules.stream()
                .filter(GenFieldRule::haveExample)
                .filter(r -> r.getNames().contains(field.getName()))
                .findAny()
                .map(GenFieldRule::getGeneratorExample);

        return (namedGenerator.isPresent())
                ? namedGenerator
                : this.rules.stream()
                        .filter(GenFieldRule::haveExample)
                        .filter(r -> field.getType().equals(r.getType()))
                        .findAny()
                        .map(GenFieldRule::getGeneratorExample);
    }

    /**
     * Retries desired generator for field
     *
     * @param field targeted
     * @return generator for named field or optional generator for specific type
     *         from rules
     */
    public @NotNull Optional<Class<? extends IGenerator>> getDesired(Field field) {
        if (field == null || isIgnored(field))
            return Optional.empty();

        final Optional<Class<? extends IGenerator>> namedGenerator = this.rules.stream()
                .filter(r -> r.getNames().contains(field.getName()))
                .findAny()
                .map(GenFieldRule::getGenerator);

        return (namedGenerator.isPresent())
                ? namedGenerator
                : this.rules.stream()
                        .filter(r -> field.getType().equals(r.getType()))
                        .findAny()
                        .map(GenFieldRule::getGenerator);

    }

    /**
     * Setups generator for fields which names are in sequence
     *
     * @param generator  to set
     * @param fieldNames fields with names to affect
     * @return same rule
     */
    public @NotNull GenRule add(IGenerator<?> generator, String... fieldNames) {
        if (fieldNames == null || fieldNames.length == 0 || generator == null)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(generator, fieldNames);
        this.rules.add(rule);
        return this;
    }

    /**
     * Setups generator for fields which types are in sequence
     *
     * @param generator to set
     * @param fieldType field type to affect
     * @return same rule
     */
    public @NotNull GenRule add(IGenerator<?> generator, Class<?> fieldType) {
        if (fieldType == null || generator == null)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(generator, fieldType);
        this.rules.add(rule);
        return this;
    }

    /**
     * Setups generator for fields which names are in sequence
     *
     * @param generator  to set
     * @param fieldNames fields with names to affect
     * @return same rule
     */
    public @NotNull GenRule add(Class<? extends IGenerator> generator, String... fieldNames) {
        if (fieldNames == null || fieldNames.length == 0 || generator == null)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(generator, fieldNames);
        this.rules.add(rule);
        return this;
    }

    /**
     * Setups generator for fields which types are in sequence
     *
     * @param generator to set
     * @param fieldType field type to affect
     * @return same rule
     */
    public @NotNull GenRule add(Class<? extends IGenerator> generator, Class<?> fieldType) {
        if (fieldType == null || generator == null)
            throw new IllegalArgumentException("Arguments can not be null or empty");

        final GenFieldRule rule = new GenFieldRule(generator, fieldType);
        this.rules.add(rule);
        return this;
    }

    /**
     * Setups fields in sequence to be ignored for generating values
     *
     * @param fieldNames fields with names to affect
     * @return same rule
     */
    public @NotNull GenRule ignore(String... fieldNames) {
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
