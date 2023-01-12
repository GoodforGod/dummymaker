package io.dummymaker.factory.refactored;

import io.dummymaker.annotation.GenDepth;
import io.dummymaker.generator.Generator;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/**
 * Rule for settings generator type for specific field name or field type
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.11.2022
 */
public final class GenRule {

    private static final Class<?> GLOBAL_MARKER = Void.class;

    private final int depth;
    private final boolean isAuto;
    private final Class<?> target;
    private final Set<String> ignored = new HashSet<>();
    private final Set<GenRuleField> fieldRules = new HashSet<>();

    private GenRule(Class<?> target, boolean isAuto, int depth) {
        this.isAuto = isAuto;
        this.target = target;
        this.depth = depth;
    }

    @NotNull
    public static GenRule manual(@NotNull Class<?> target) {
        return manual(target, GenDepth.DEFAULT);
    }

    @NotNull
    public static GenRule manual(@NotNull Class<?> target, int depth) {
        if (GLOBAL_MARKER.equals(target))
            throw new IllegalArgumentException("Void can't be Rule type");

        return new GenRule(target, false, depth);
    }

    @NotNull
    public static GenRule auto(@NotNull Class<?> target) {
        return auto(target, GenDepth.DEFAULT);
    }

    @NotNull
    public static GenRule auto(@NotNull Class<?> target, int depth) {
        if (GLOBAL_MARKER.equals(target))
            throw new IllegalArgumentException("Void can't be Rule type");

        return new GenRule(target, true, depth);
    }

    @NotNull
    public static GenRule global() {
        return new GenRule(GLOBAL_MARKER, true, GenDepth.DEFAULT);
    }

    @NotNull
    public static GenRule global(int depth) {
        return new GenRule(GLOBAL_MARKER, true, depth);
    }

    @NotNull
    public GenRule named(@NotNull Supplier<Generator<?>> generatorSupplier, @NotNull String... fieldNames) {
        final GenRuleField rule = new GenRuleField(generatorSupplier, fieldNames);
        this.fieldRules.add(rule);
        return this;
    }

    @NotNull
    public GenRule typed(@NotNull Supplier<Generator<?>> generatorSupplier, @NotNull Class<?> fieldType) {
        final GenRuleField rule = new GenRuleField(generatorSupplier, fieldType);
        this.fieldRules.add(rule);
        return this;
    }

    @NotNull
    public GenRule ignore(@NotNull String... fieldNames) {
        this.ignored.addAll(Arrays.asList(fieldNames));
        return this;
    }

    @NotNull
    Optional<Generator<?>> find(Field field) {
        if (field == null || isIgnored(field))
            return Optional.empty();

        final Optional<Generator<?>> namedGenerator = fieldRules.stream()
                .filter(r -> r.getNames().contains(field.getName()))
                .findAny()
                .map(rule -> rule.getGeneratorSupplier().get());

        if (namedGenerator.isPresent()) {
            return namedGenerator;
        }

        return find(field.getType());
    }

    @NotNull
    Optional<Generator<?>> find(Class<?> type) {
        if (type == null)
            return Optional.empty();

        final Optional<? extends Generator<?>> equalType = fieldRules.stream()
                .filter(GenRuleField::isTyped)
                .filter(rule -> type.equals(rule.getType()))
                .findAny()
                .map(r -> r.getGeneratorSupplier().get());

        if(equalType.isPresent()) {
            return Optional.of(equalType.get());
        }

        return fieldRules.stream()
                .filter(GenRuleField::isTyped)
                .filter(rule -> type.isAssignableFrom(rule.getType()))
                .findAny()
                .map(r -> r.getGeneratorSupplier().get());
    }

    @NotNull
    GenRule merge(@NotNull GenRule rule) {
        if (!getTarget().equals(rule.getTarget())) {
            return this;
        }

        for (GenRuleField fieldRule : rule.fieldRules) {
            for (GenRuleField innerFieldRule : fieldRules) {
                if (fieldRule.equals(innerFieldRule) && fieldRule.isTyped()) {
                    throw new IllegalArgumentException("Multiple Rules describe same type: " + fieldRule.getType());
                }
            }
        }

        this.fieldRules.addAll(rule.fieldRules);
        this.ignored.addAll(rule.ignored);
        return this;
    }

    boolean isGlobal() {
        return GLOBAL_MARKER.equals(target);
    }

    boolean isIgnored(Field field) {
        return ignored.contains(field.getName());
    }

    Optional<Integer> getDepth() {
        return Optional.of(depth);
    }

    boolean isAuto() {
        return isAuto;
    }

    Class<?> getTarget() {
        return target;
    }

    Set<String> getIgnored() {
        return ignored;
    }
}
