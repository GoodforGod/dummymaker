package io.dummymaker;

import io.dummymaker.annotation.GenDepth;
import io.dummymaker.generator.Generator;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Rule for settings generator type for specific field name or field type
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.11.2022
 */
public final class GenRule {

    private static final Class<?> GLOBAL_MARKER = Void.class;

    private final Integer depth;
    private final Boolean isAuto;
    private final Class<?> target;
    private final Set<String> ignored = new HashSet<>();
    private final Set<GenRuleField> fieldRules = new HashSet<>();

    private GenRule(Class<?> target, Boolean isAuto, Integer depth) {
        this.isAuto = isAuto;
        this.target = target;
        this.depth = depth;
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target) {
        return ofClassInner(target, null, null);
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, boolean isAuto) {
        return ofClassInner(target, isAuto, null);
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, int depth) {
        return ofClassInner(target, null, depth);
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, boolean isAuto, int depth) {
        return ofClassInner(target, isAuto, depth);
    }

    private static GenRule ofClassInner(@NotNull Class<?> target, @Nullable Boolean isAuto, @Nullable Integer depth) {
        if (GLOBAL_MARKER.equals(target)) {
            throw new IllegalArgumentException(GLOBAL_MARKER + " can't be GenRule type");
        }

        if (depth != null && (depth < 1 || depth > GenDepth.MAX)) {
            throw new IllegalArgumentException("Depth must be between 1 and 50, but was " + depth + " for " + target);
        }

        return new GenRule(target, isAuto, depth);
    }

    @NotNull
    public static GenRule ofGlobal() {
        return new GenRule(GLOBAL_MARKER, null, null);
    }

    @NotNull
    public GenRule registerFields(@NotNull Supplier<Generator<?>> generatorSupplier, @NotNull String... fieldNames) {
        final GenRuleField rule = new GenRuleField(generatorSupplier, fieldNames);
        this.fieldRules.add(rule);
        return this;
    }

    @NotNull
    public GenRule registerType(@NotNull Supplier<Generator<?>> generatorSupplier, @NotNull Class<?> fieldType) {
        final GenRuleField rule = new GenRuleField(generatorSupplier, fieldType);
        this.fieldRules.add(rule);
        return this;
    }

    @NotNull
    public GenRule excludeFields(@NotNull String... fieldNames) {
        this.ignored.addAll(Arrays.asList(fieldNames));
        return this;
    }

    @NotNull
    Optional<Generator<?>> find(Field field) {
        if (field == null || isIgnored(field)) {
            return Optional.empty();
        }

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
        if (type == null) {
            return Optional.empty();
        }

        final Optional<? extends Generator<?>> equalType = fieldRules.stream()
                .filter(GenRuleField::isTyped)
                .filter(rule -> type.equals(rule.getType()))
                .findAny()
                .map(r -> r.getGeneratorSupplier().get());

        if (equalType.isPresent()) {
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
        return Optional.ofNullable(depth);
    }

    Optional<Boolean> isAuto() {
        return Optional.ofNullable(isAuto);
    }

    Class<?> getTarget() {
        return target;
    }

    Set<String> getIgnored() {
        return ignored;
    }

    Set<GenRuleField> getFieldRules() {
        return fieldRules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenRule))
            return false;
        GenRule genRule = (GenRule) o;
        return depth == genRule.depth && isAuto == genRule.isAuto && Objects.equals(target, genRule.target)
                && Objects.equals(ignored, genRule.ignored) && Objects.equals(fieldRules, genRule.fieldRules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, isAuto, target, ignored, fieldRules);
    }
}
